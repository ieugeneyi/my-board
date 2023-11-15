package com.geneyi.dto.todo;

import com.geneyi.domain.todo.Category;
import com.geneyi.domain.todo.ToDo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToDoRequestDto {

    private Category category;
    private String content;
    private Integer sort;
    private boolean completed;

    @Builder
    public ToDoRequestDto(String content, Integer sort, boolean completed, Category category) {
        this.category = category;
        this.content = content;
        this.sort = sort == null ? 0 : sort;
        this.completed = completed;
    }

    public ToDoRequestDto(ToDo toDo){
        this.category = toDo.getCategory();
        this.content = toDo.getContent();
        this.sort = toDo.getSort();
        this.completed = toDo.getCompleted();
    }

    public ToDo toEntity(){
        return ToDo.builder()
              .category(category)
              .content(content)
              .sort(sort)
              .completed(completed)
              .build();
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
