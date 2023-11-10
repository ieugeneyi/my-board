package com.geneyi.dto.todo;

import com.geneyi.domain.todo.ToDo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToDoRequestDto {

    private String content;
    private boolean completed;

    @Builder
    public ToDoRequestDto(String content, boolean completed) {
        this.content = content;
        this.completed = completed;
    }

    public ToDo toEntity(){
        return ToDo.builder()
              .content(content)
              .build();
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
