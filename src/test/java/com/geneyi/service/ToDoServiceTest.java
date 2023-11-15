package com.geneyi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geneyi.domain.todo.Category;
import com.geneyi.domain.todo.CategoryRepository;
import com.geneyi.domain.todo.ToDo;
import com.geneyi.domain.todo.ToDoRepository;
import com.geneyi.dto.todo.ToDoRequestDto;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ToDoServiceTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToDoService toDoService;
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Test
    public void toDo_순서저장() throws Exception {
        //given
        Category cate1 = new Category("cate1");
        categoryRepository.save(cate1);
        ToDo todo1 = new ToDo(cate1, "todo1", 1, false);
        ToDo todo2 = new ToDo(cate1, "todo2", 2, false);
        toDoRepository.save(todo1);
        toDoRepository.save(todo2);

        ToDoRequestDto toDoRequestDto1 = new ToDoRequestDto(todo1);
        ToDoRequestDto toDoRequestDto2 = new ToDoRequestDto(todo2);

        //when
        toDoRequestDto1.setSort(2);
        toDoRequestDto2.setSort(1);

        List<ToDoRequestDto> toDos = new ArrayList<>();
        toDos.add(toDoRequestDto1);
        toDos.add(toDoRequestDto2);

        toDoService.saveSort(toDos);

        //then
        assertThat(toDoService.findAll().get(0).getContent()).isEqualTo("todo2");

    }
}