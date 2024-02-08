package com.pavan.todo.services;

import java.util.List;

import com.pavan.todo.models.Todo;
import com.pavan.todo.repositories.TodoRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    private Counter todoCounter;
    private MeterRegistry meterRegistry;

    public TodoService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.todoCounter = Counter.builder("todo.created.count")
                .tags("status", "created")
                .description("Total number of todo items created")
                .register(meterRegistry);
    }

    public List<Todo> findAll() {
        Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "createdAt");
        return todoRepository.findAll(sortByCreatedAtDesc);
    }

    public Todo createTodo(Todo todo) {
        todo.setCompleted(false);
        Todo createdTodo = todoRepository.save(todo);
        incrementTodoCreatedCounter();
        return createdTodo;
    }

    public Todo completeTodo(String id) {
        return todoRepository.findById(id).map(todoData -> {
            todoData.setCompleted(true);
            Todo updatedTodo = todoRepository.save(todoData);
            return updatedTodo;
        }).orElse(null);
    }

    public ResponseEntity<Todo> updateTodo(String id, Todo todo) {
        return todoRepository.findById(id).map(todoData -> {
            todoData.setTitle(todo.getTitle());
            todoData.setCompleted(todo.isCompleted());
            Todo updatedTodo = todoRepository.save(todoData);
            return ResponseEntity.ok().body(updatedTodo);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deleteTodo(String id) {
        return todoRepository.findById(id).map(todo -> {
            todoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @Scheduled(fixedRate = 3000)
    public void updatePendingTodoMetrics() {
        long pendingTodoCount = todoRepository.countByCompleted(false);
        Tags tags = Tags.of("status", "pending");
        meterRegistry.gauge("todo.pending.count", tags, pendingTodoCount);
    }

    public void incrementTodoCreatedCounter() {
        todoCounter.increment();
    }

}
