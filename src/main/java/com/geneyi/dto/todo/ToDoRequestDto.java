package com.geneyi.dto.todo;

import com.geneyi.domain.todo.ToDo;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ToDoRequestDto {

    @NotBlank(message = "CONTENT_IS_MANDATORY")
    private String content;

    public ToDoRequestDto(String content) {
        this.content = content;
    }

    public ToDo toEntity(){
        return ToDo.builder()
              .content(content)
              .build();
    }
}
