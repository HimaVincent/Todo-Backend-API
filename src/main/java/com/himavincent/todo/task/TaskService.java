package com.himavincent.todo.task;

import java.util.List;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

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
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findByArchivedFalse()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public TaskResponseDto createTask(CreateTaskDto dto) {
        Task task = new Task();

        task.setTitle(validateTitle(dto.getTitle()));
        task.setNotes(dto.getNotes());
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
        return mapToResponseDto(savedTask);
    }

    public TaskResponseDto updateTask(Long id, UpdateTaskDto dto) {

        Task task = findActiveTaskById(id);

        String title = dto.getTitle();
        String notes = dto.getNotes();
        Long categoryId = dto.getCategoryId();

        if (title != null) {
            task.setTitle(validateTitle(title));
        }

        if (notes != null) {
            task.setNotes(notes);
        }

        if (dto.getDueAt() != null) {
            task.setDueAt(dto.getDueAt());
        }

        if (categoryId != null) {
            Category category = findCategoryById(categoryId);
            task.setCategory(category);
        }

        Task updatedTask = taskRepository.save(task);

        return mapToResponseDto(updatedTask);
    }

    public TaskResponseDto updateTaskCompletion(Long id, UpdateTaskCompletionDto dto) {
        Task task = findActiveTaskById(id);

        task.setCompleted(dto.getCompleted());

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDto(updatedTask);
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

    private String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("Title is required");
        }
        return title;
    }

    private TaskResponseDto mapToResponseDto(Task task) {
        TaskResponseDto dto = modelMapper.map(task, TaskResponseDto.class);

        if (task.getCategory() != null) {
            dto.setCategoryId(task.getCategory().getId());
            dto.setCategoryName(task.getCategory().getName());
        } else {
            dto.setCategoryId(null);
            dto.setCategoryName(null);
        }

        return dto;
    }
}