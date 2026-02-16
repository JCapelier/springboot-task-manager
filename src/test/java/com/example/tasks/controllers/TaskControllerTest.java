package com.example.tasks.controllers;

import com.example.tasks.domain.*;
import com.example.tasks.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  private ObjectMapper objectMapper;
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
  void testGetAllTasks() throws Exception {
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

  @Test
  void testGetTask() throws Exception{
    Task task = taskRepository.findAll().get(0);
    mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + task.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()));
  }

  @Test
  void testUpdateTask() throws Exception{
    Task task = taskRepository.findAll().get(0);
    Task updatedTask = new Task("Updated Title");
    updatedTask.changeStatus();
    String updatedJson = objectMapper.writeValueAsString(updatedTask);

    mockMvc.perform(MockMvcRequestBuilders.put("/tasks/" + task.getId())
            .contentType("application/json")
            .content(updatedJson))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedTask.getTitle()))
          .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(updatedTask.getStatus()));
  }
}
