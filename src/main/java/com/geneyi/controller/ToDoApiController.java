package com.geneyi.controller;

import com.geneyi.dto.todo.ToDoRequestDto;
import com.geneyi.dto.todo.ToDoResponseDto;
import com.geneyi.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
@RestController
public class ToDoApiController {

    private final ToDoService toDoService;

    @GetMapping
    public List<ToDoResponseDto> findAll(){
        return toDoService.findAll();
    }

    @PostMapping
    public ResponseEntity<String> save(@Validated  @RequestBody ToDoRequestDto requestDto,
                               BindingResult bindingResult){
        toDoService.save(requestDto);
        return ResponseEntity.ok("saved");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        toDoService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<String> complete(@Validated  @RequestBody ToDoRequestDto requestDto, @PathVariable Long id){
        log.info(String.valueOf(requestDto.isCompleted()));
        toDoService.checkComplete(requestDto.isCompleted(), id);
        return ResponseEntity.ok("complete");
    }

    @PostMapping("/sort")
    public ResponseEntity<String> sort(@RequestBody List<ToDoRequestDto> dtoList){
        toDoService.saveSort(dtoList);
        return ResponseEntity.ok("sort saved");
    }
}
