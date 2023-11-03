package com.geneyi.domain.todo;

import com.geneyi.domain.BaseTimeEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ToDo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private Boolean completed;

    @PostConstruct
    public void init() {
        this.completed = false;
    }

    @Builder
    public ToDo(String content, Boolean completed) {
        this.content = content;
        this.completed = completed != null && completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
