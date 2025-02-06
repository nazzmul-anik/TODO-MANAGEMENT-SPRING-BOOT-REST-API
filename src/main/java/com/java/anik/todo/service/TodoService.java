package com.java.anik.todo.service;

import com.java.anik.todo.dto.TodoDto;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long id);
}
