package com.simran.todo.service;

import com.simran.todo.dto.TodoDto;
import com.simran.todo.entity.Todo;
import com.simran.todo.exception.ResourceNotFound;
import com.simran.todo.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    ModelMapper modelMapper;

    public TodoDto addTodo(TodoDto todoDto) {
//        Todo todo = new Todo();
//        todo.setId(todoDto.getId());
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());

        //using model Mapper
        Todo todo = modelMapper.map(todoDto,Todo.class);
        Todo savedTodo = todoRepository.save(todo);

        TodoDto savedTodoDto = modelMapper.map(savedTodo,TodoDto.class);

//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setCompleted(savedTodo.isCompleted());
//        savedTodoDto.setDescription(savedTodo.getDescription());
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());

        return savedTodoDto;
    }

    //http://localhost:8080/api/todos/get/3
    public TodoDto getTodo(Long id) {
        Todo findTodo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFound("Todo with id "+id+" doesnot exist"));
        return modelMapper.map(findTodo,TodoDto.class);
    }

    public List<TodoDto> getAllTodos() {
        List<Todo> todoList = todoRepository.findAll();
        List<TodoDto> todoDtoList = new ArrayList<>();
        todoList.forEach((todo)->todoDtoList.add(modelMapper.map(todo,TodoDto.class)));
        return todoDtoList;
    }

    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo existingTodo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFound("Todo with id "+id+" doesnot exist"));
        existingTodo.setTitle(todoDto.getTitle());
        existingTodo.setDescription(todoDto.getDescription());
        existingTodo.setCompleted(todoDto.isCompleted());
        Todo updateTodo = todoRepository.save(existingTodo);
        return modelMapper.map(updateTodo,TodoDto.class);
    }

    public void deleteTodo(Long id) {
        Todo todoToDelete = todoRepository.findById(id).orElseThrow(()->new ResourceNotFound("Todo with id "+id+" doesnot exist"));
        todoRepository.delete(todoToDelete);
    }

    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFound("Todo with id "+id+" doesnot exist"));
        todo.setCompleted(true);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo,TodoDto.class);
    }

    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFound("Todo with id "+id+" doesnot exist"));
        todo.setCompleted(false);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo,TodoDto.class);
    }
}
