package com.geneyi.domain.todo;

import com.geneyi.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="category", fetch = FetchType.EAGER)
    private List<ToDo> todoList;

    @Builder
    public Category(String name){
        this.name = name;
    }

}
