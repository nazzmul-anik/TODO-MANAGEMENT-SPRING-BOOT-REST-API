package com.java.anik.todo.service.impl;

import com.java.anik.todo.dto.TodoDto;
import com.java.anik.todo.entity.Todo;
import com.java.anik.todo.exception.ResourceNotFoundException;
import com.java.anik.todo.repository.TodoRepository;
import com.java.anik.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    @Autowired
    private ModelMapper modelMapper;
    private TodoRepository todoRepository;


    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        // convert TodoDto into Todo JPA entity
        Todo todo = modelMapper.map(todoDto, Todo.class);

        // Todo JPA entity save on DB
        Todo savedTodo = todoRepository.save(todo);

        // Convert saved Todo JPA entity object into TodoDto object
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo =  todoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+ id));
        return modelMapper.map(todo, TodoDto.class);
    }
}
