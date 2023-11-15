package com.geneyi.controller;

import com.geneyi.dto.todo.ToDoRequestDto;
import com.geneyi.dto.todo.ToDoResponseDto;
import com.geneyi.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
@RestController
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping
    public List<ToDoResponseDto> findAll(){
        return toDoService.findAll();
    }

    @PostMapping
    public Long save(@Validated  @RequestBody ToDoRequestDto requestDto,
                     BindingResult bindingResult){
        return toDoService.save(requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        toDoService.delete(id);
    }

    @PostMapping("/{id}/complete")
    public void complete(@Validated  @RequestBody ToDoRequestDto requestDto, @PathVariable Long id){
        toDoService.checkComplete(requestDto.isCompleted(), id);
    }

    @PostMapping("/sort")
    public void sort(@RequestBody List<ToDoRequestDto> dtoList){
        toDoService.saveSort(dtoList);
    }
}
