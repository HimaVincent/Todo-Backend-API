package com.himavincent.todo.task;

import java.util.List;

import org.springframework.stereotype.Service;

import com.himavincent.todo.category.entities.Category;
import com.himavincent.todo.category.CategoryRepository;
import com.himavincent.todo.common.exception.BadRequestException;
import com.himavincent.todo.common.exception.NotFoundException;
import com.himavincent.todo.task.dtos.CreateTaskDto;
import com.himavincent.todo.task.dtos.TaskResponseDto;
import com.himavincent.todo.task.dtos.UpdateTaskCompletionDto;
import com.himavincent.todo.task.dtos.UpdateTaskDto;
import com.himavincent.todo.task.entities.Task;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findByArchivedFalse()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public TaskResponseDto createTask(CreateTaskDto dto) {
        Task task = new Task();

        task.setTitle(trimAndValidateTitle(dto.getTitle()));
        task.setNotes(trimNotes(dto.getNotes()));
        task.setDueAt(dto.getDueAt());
        task.setCompleted(false);
        task.setArchived(false);

        if (dto.getCategoryId() != null) {
            Category category = findCategoryById(dto.getCategoryId());
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        Task savedTask = taskRepository.save(task);
        return toResponseDto(savedTask);
    }

    public TaskResponseDto updateTask(Long id, UpdateTaskDto dto) {

        Task task = findActiveTaskById(id);

        String title = dto.getTitle();
        String notes = dto.getNotes();
        Long categoryId = dto.getCategoryId();

        if (title != null) {
            task.setTitle(trimAndValidateTitle(title));
        }

        if (notes != null) {
            task.setNotes(trimNotes(notes));
        }

        if (dto.getDueAt() != null) {
            task.setDueAt(dto.getDueAt());
        }

        if (categoryId != null) {
            Category category = findCategoryById(categoryId);
            task.setCategory(category);
        }

        Task updatedTask = taskRepository.save(task);

        return toResponseDto(updatedTask);
    }

    public TaskResponseDto updateTaskCompletion(Long id, UpdateTaskCompletionDto dto) {
        Task task = findActiveTaskById(id);

        task.setCompleted(dto.getCompleted());

        Task updatedTask = taskRepository.save(task);
        return toResponseDto(updatedTask);
    }

    public void deleteTask(Long id) {
        Task task = findActiveTaskById(id);

        task.setArchived(true);
        taskRepository.save(task);
    }

    private Task findActiveTaskById(Long id) {
        return taskRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    private String trimAndValidateTitle(String title) {
        String trimmedTitle = title == null ? null : title.trim();

        if (trimmedTitle == null || trimmedTitle.isBlank()) {
            throw new BadRequestException("Title is required");
        }

        return trimmedTitle;
    }

    private String trimNotes(String notes) {
        if (notes == null) {
            return null;
        }

        String trimmedNotes = notes.trim();
        return trimmedNotes.isEmpty() ? null : trimmedNotes;
    }

    private TaskResponseDto toResponseDto(Task task) {
        Long categoryId = null;
        String categoryName = null;

        if (task.getCategory() != null) {
            categoryId = task.getCategory().getId();
            categoryName = task.getCategory().getName();
        }

        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getNotes(),
                task.getDueAt(),
                task.isCompleted(),
                categoryId,
                categoryName);
    }
}