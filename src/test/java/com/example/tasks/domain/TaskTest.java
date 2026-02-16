package com.example.tasks.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testConstructorAndGetters() {
        String title = "Learning Spring Boot";
        Task task = new Task(title);
        assertNull(task.getId());
        assertEquals(title, task.getTitle());
        assertFalse(task.getStatus());
    }

    @Test
    void testSetters() {
        Task task = new Task();
        task.setTitle("Write tests");
        task.changeStatus();
        assertEquals("Write tests", task.getTitle());
        assertTrue(task.getStatus());
    }
}
