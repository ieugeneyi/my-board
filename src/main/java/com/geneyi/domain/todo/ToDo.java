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

    @ManyToOne
    private Category category;

    @Column
    private String content;

    @Column
    private Integer sort;

    @Column
    private Boolean completed;

    @PostConstruct
    public void init() {
        this.completed = false;
        this.sort = 0;
    }

    @Builder
    public ToDo(Category category, String content, Integer sort, Boolean completed) {
        this.category = category;
        this.content = content;
        this.sort = sort == null ? 0 : sort;
        this.completed = completed != null && completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void changeCategory(Category category) {
        this.category = category;
        if (category != null){
            category.getTodoList().add(this);
        }
    }

}
