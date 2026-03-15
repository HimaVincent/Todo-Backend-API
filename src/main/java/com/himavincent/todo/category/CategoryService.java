package com.himavincent.todo.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.himavincent.todo.category.dtos.CategoryResponseDto;
import com.himavincent.todo.category.dtos.CreateCategoryDto;
import com.himavincent.todo.category.dtos.UpdateCategoryDto;
import com.himavincent.todo.category.entities.Category;
import com.himavincent.todo.common.enums.DeleteCategoryMode;
import com.himavincent.todo.common.exception.BadRequestException;
import com.himavincent.todo.common.exception.NotFoundException;
import com.himavincent.todo.task.TaskRepository;
import com.himavincent.todo.task.entities.Task;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    public CategoryService(CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
    }

    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> new CategoryResponseDto(
                        category.getId(),
                        category.getName()))
                .toList();
    }

    public CategoryResponseDto createCategory(CreateCategoryDto data) {

        String name = data.getName().trim();

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponseDto(
                savedCategory.getId(),
                savedCategory.getName());
    }

    public CategoryResponseDto updateCategory(Long id, UpdateCategoryDto data) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        String name = data.getName().trim();

        if (categoryRepository.existsByNameIgnoreCase(name)
                && !category.getName().equalsIgnoreCase(name)) {
            throw new BadRequestException("Category already exists");
        }

        category.setName(name);

        Category updatedCategory = categoryRepository.save(category);

        return new CategoryResponseDto(
                updatedCategory.getId(),
                updatedCategory.getName());
    }

    @Transactional
    public void deleteCategory(Long id, DeleteCategoryMode mode) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        List<Task> tasks = taskRepository.findByCategoryId(id);

        switch (mode) {
            case KEEP_TASKS:
                for (Task task : tasks) {
                    task.setCategory(null);
                }
                taskRepository.saveAll(tasks);
                break;

            case DELETE_ALL_TASKS:
                for (Task task : tasks) {
                    task.setArchived(true);
                    task.setCategory(null);
                }
                taskRepository.saveAll(tasks);
                break;

            default:
                throw new BadRequestException("Invalid delete mode");
        }

        categoryRepository.delete(category);
    }

}
