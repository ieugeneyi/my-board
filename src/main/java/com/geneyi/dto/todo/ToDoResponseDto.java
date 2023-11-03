package com.geneyi.dto.todo;

import com.geneyi.domain.todo.ToDo;
import lombok.Getter;

@Getter
public class ToDoResponseDto {

    private Long id;
    private String content;
    private Boolean completed;

    public ToDoResponseDto(ToDo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.completed = todo.getCompleted();
    }
}
