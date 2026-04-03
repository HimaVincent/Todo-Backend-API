package com.himavincent.todo.category;

import java.util.List;

import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, TaskRepository taskRepository,
            ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }

    public CategoryResponseDto createCategory(CreateCategoryDto data) {

        String name = data.getName();

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryResponseDto.class);
    }

    public CategoryResponseDto updateCategory(Long id, UpdateCategoryDto data) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        String name = data.getName();

        if (categoryRepository.existsByNameIgnoreCase(name)
                && !category.getName().equalsIgnoreCase(name)) {
            throw new BadRequestException("Category already exists");
        }

        category.setName(name);

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryResponseDto.class);
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
