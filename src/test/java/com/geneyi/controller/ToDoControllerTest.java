package com.geneyi.controller;

import com.geneyi.domain.todo.ToDo;
import com.geneyi.domain.todo.ToDoRepository;
import com.geneyi.dto.todo.ToDoRequestDto;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoControllerTest {

    @Autowired
    private ToDoRepository toDoRepository;

    @Transactional
    @Test
    public void ToDo_등록() throws Exception{
        //given
        String content = "test";
        ToDoRequestDto toDoRequestDto = new ToDoRequestDto(content);

        //when
        toDoRepository.save(toDoRequestDto.toEntity());

        //then
        List<ToDo> all = toDoRepository.findAll();
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);
    }



}