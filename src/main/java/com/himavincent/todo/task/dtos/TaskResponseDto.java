package com.himavincent.todo.task.dtos;

import java.time.LocalDateTime;

public class TaskResponseDto {

    private Long id;
    private String title;
    private String notes;
    private LocalDateTime dueAt;
    private boolean completed;
    private LocalDateTime completedAt;
    private Long categoryId;
    private String categoryName;

    public TaskResponseDto() {
    }

    public TaskResponseDto(Long id, String title, String notes, LocalDateTime dueAt,
            boolean completed, Long categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.dueAt = dueAt;
        this.completed = completed;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
