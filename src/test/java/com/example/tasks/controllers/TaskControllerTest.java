package com.example.tasks.controllers;

import com.example.tasks.domain.*;
import com.example.tasks.repository.TaskRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTest {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private MockMvc mockMvc;

  private void createTasksSeeds() {
    for (int i = 0; i < 5; i++) {
      String title = "Task " + i;
      taskRepository.save(new Task(title));
    }
  }

  @BeforeEach
  void setUp() {
    createTasksSeeds();
  }

  @Test
  void testGetTasks() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testPostTasks() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
        .contentType("application/json")
        .content("{\"title\":\"New task\"}"))
        .andExpect(MockMvcResultMatchers.status().isOk());

        assertTrue(
        taskRepository.findAll().stream()
            .anyMatch(task -> "New task".equals(task.getTitle())));
  };
}
