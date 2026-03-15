package com.himavincent.todo.task.entities;

import java.time.LocalDateTime;

import com.himavincent.todo.category.entities.Category;
import com.himavincent.todo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(nullable = false, length = 80)
    private String title;

    @Column(length = 180)
    private String notes;

    @Column
    private LocalDateTime dueAt;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false)
    private boolean archived = false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Task() {
    }

    public Task(String title, String notes, LocalDateTime dueAt, boolean completed, boolean archived,
            Category category) {

        this.title = title;
        this.notes = notes;
        this.dueAt = dueAt;
        this.completed = completed;
        this.archived = archived;
        this.category = category;
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
