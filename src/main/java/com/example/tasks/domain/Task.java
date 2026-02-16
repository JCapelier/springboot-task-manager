package com.example.tasks.domain;

import jakarta.persistence.*;

@Entity
public class Task {
  @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private boolean status;

  public Task() {}

  public Task(String title) {
    this.title = title;
    this.status = false;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) {
    title = newTitle;
  }

  public Long getId() {
    return id;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void changeStatus() {
    if (status == true) {
      status = false;
    } else {
      status = true;
    }
  }
}
