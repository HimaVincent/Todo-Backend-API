package com.himavincent.todo.task.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskDto {

    @NotBlank(message = "Title is required")
    @Size(max = 80, message = "Title must be at most 80 characters")
    private String title;

    @Size(max = 180, message = "Notes must be at most 180 characters")
    private String notes;

    private LocalDateTime dueAt;

    private Long categoryId;

    public CreateTaskDto() {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}