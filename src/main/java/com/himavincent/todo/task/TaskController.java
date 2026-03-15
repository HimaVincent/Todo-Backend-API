package com.himavincent.todo.task;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.himavincent.todo.task.dtos.CreateTaskDto;
import com.himavincent.todo.task.dtos.TaskResponseDto;
import com.himavincent.todo.task.dtos.UpdateTaskCompletionDto;
import com.himavincent.todo.task.dtos.UpdateTaskDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@Valid @RequestBody CreateTaskDto dto) {
        return taskService.createTask(dto);
    }

    @PatchMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskDto dto) {
        return taskService.updateTask(id, dto);
    }

    @PatchMapping("/{id}/completion")
    public TaskResponseDto updateTaskCompletion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskCompletionDto dto) {
        return taskService.updateTaskCompletion(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
