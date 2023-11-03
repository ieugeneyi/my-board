package com.geneyi.controller;

import com.geneyi.dto.todo.ToDoRequestDto;
import com.geneyi.dto.todo.ToDoResponseDto;
import com.geneyi.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void complete(@RequestBody boolean isComplete, @PathVariable Long id){
        toDoService.checkComplete(isComplete, id);
    }
}
