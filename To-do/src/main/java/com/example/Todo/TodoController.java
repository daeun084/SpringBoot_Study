package com.example.Todo;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private List<Todo> todos = new ArrayList<>();

    @GetMapping
    public List<Todo> getAllTodos() {
        return todos;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        // 새로운 To-Do 항목 생성 로직
        todos.add(todo);
        return todo;
    }
}

