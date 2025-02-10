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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> allTodos = todoRepository.findAll();

        return allTodos.stream()
                .map((todo) -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
