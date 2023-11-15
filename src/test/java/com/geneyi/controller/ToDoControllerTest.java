package com.geneyi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geneyi.domain.todo.Category;
import com.geneyi.domain.todo.CategoryRepository;
import com.geneyi.domain.todo.ToDo;
import com.geneyi.domain.todo.ToDoRepository;
import com.geneyi.dto.todo.ToDoRequestDto;
import com.geneyi.dto.todo.ToDoResponseDto;
import com.geneyi.service.ToDoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToDoService toDoService;
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @WithMockUser
    @Test
    public void ToDo_등록() throws Exception{
        //given
        String content = "test";
        ToDoRequestDto toDoRequestDto = ToDoRequestDto.builder()
                                            .content(content).build();

        //when
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:" + port + "/api/v1/todos")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(toDoRequestDto))
                      .with(csrf())
        ).andExpect(status().isOk());


        //then
        List<ToDoResponseDto> all = toDoService.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Transactional
    @WithMockUser
    @Test
    public void ToDo_check() throws Exception{
        //given
        Category category = Category.builder()
                .name("category")
                .build();
        categoryRepository.save(category);
        System.out.println("================================================");
        System.out.println(objectMapper.writeValueAsString(category));
        System.out.println("================================================");
        String content = "test";
        ToDoRequestDto toDoRequestDto = ToDoRequestDto.builder()
                .category(category)
                .content(content).build();
        Long saved = toDoService.save(toDoRequestDto);
        toDoRequestDto.setCompleted(true);

        System.out.println("================================================");
        System.out.println(objectMapper.writeValueAsString(toDoRequestDto));
        System.out.println("================================================");

        //when
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:" + port + "/api/v1/todos/" + saved + "/complete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoRequestDto))
                        .with(csrf())
        ).andExpect(status().isOk());

        //then
        ToDo toDo = toDoRepository.findById(saved).orElse(null);
        assertThat(toDo).isNotNull();
        assertThat(toDo.getCompleted()).isEqualTo(true);

        System.out.println("================================================");
        System.out.println(objectMapper.writeValueAsString(toDo));
        System.out.println(toDo.getCompleted());
        System.out.println("================================================");

    }
}