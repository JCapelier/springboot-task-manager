package com.example.tasks.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import com.example.tasks.domain.Task;
import com.example.tasks.repository.TaskRepository;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskRepository taskRepository;

  @GetMapping
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    if (task == null) {
      return ResponseEntity.badRequest().build();
    }

    Task savedTask = taskRepository.save(task);
    return ResponseEntity.ok(savedTask);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTask(@PathVariable Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    return taskRepository.findById(id)
        .map(task -> ResponseEntity.ok(task))
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Task> optionalTask = taskRepository.findById(id);

    if (optionalTask.isPresent()) {
      Task task = optionalTask.get();
      task.setTitle(updatedTask.getTitle());
      task.setStatus(updatedTask.getStatus());
      taskRepository.save(task);
      return ResponseEntity.ok(task);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    if (taskRepository.existsById(id)) {
      taskRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
