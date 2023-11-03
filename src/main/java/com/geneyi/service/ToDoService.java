package com.geneyi.service;

import com.geneyi.domain.todo.ToDo;
import com.geneyi.domain.todo.ToDoRepository;
import com.geneyi.dto.todo.ToDoRequestDto;
import com.geneyi.dto.todo.ToDoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ToDoService {
    private final ToDoRepository todoRepository;

    public Long save(ToDoRequestDto requestDto){
        return todoRepository.save(requestDto.toEntity()).getId();
    }

    public List<ToDoResponseDto> findAll(){
        return todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream().map(ToDoResponseDto::new).toList();
    }

    public void checkComplete(boolean isComplete, Long id){
        ToDo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not exists id=" + id));
        todo.setCompleted(isComplete);
        todoRepository.save(todo);
    }

    public void delete(Long id){
        ToDo toDo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not exists id=" + id));
        todoRepository.delete(toDo);
    }






}
