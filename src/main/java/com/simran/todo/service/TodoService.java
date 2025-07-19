package com.simran.todo.service;

import com.simran.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    public TodoDto addTodo(TodoDto todoDto);
    public TodoDto getTodo(Long id);
    public List<TodoDto> getAllTodos();
    public TodoDto updateTodo(TodoDto todoDto, Long id);
    public void deleteTodo(Long id);
    public TodoDto completeTodo(Long id);
    public TodoDto inCompleteTodo(Long id);
}
