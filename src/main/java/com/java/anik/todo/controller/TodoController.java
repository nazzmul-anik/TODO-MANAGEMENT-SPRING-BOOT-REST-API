package com.java.anik.todo.controller;

import com.java.anik.todo.dto.TodoDto;
import com.java.anik.todo.repository.TodoRepository;
import com.java.anik.todo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {

    private final TodoService todoService;
    private final TodoRepository todoRepository;

    public TodoController(TodoService todoService, TodoRepository todoRepository){
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    // Build Add Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodo = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Build Get Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long id){
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Build Get All Todos REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todoDtoList = todoService.getAllTodos();
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }

    // Build Update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long id){
        TodoDto updatedTodo = todoService.updateTodo(todoDto, id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    // Build Delete Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>("Todo Deleted Successfully.", HttpStatus.OK);
    }

    // Build Complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long id){
        TodoDto updatedTodo = todoService.completeTodo(id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    // Build In Complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/incomplete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long id){
        TodoDto updatedTodo = todoService.inCompleteTodo(id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
}
