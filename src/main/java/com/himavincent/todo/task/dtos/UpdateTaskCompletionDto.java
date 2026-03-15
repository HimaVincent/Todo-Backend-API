package com.himavincent.todo.task.dtos;

import jakarta.validation.constraints.NotNull;

public class UpdateTaskCompletionDto {

    @NotNull(message = "Completed is required")
    private Boolean completed;

    public UpdateTaskCompletionDto() {
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}