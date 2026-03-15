package com.himavincent.todo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.himavincent.todo.category.dtos.CategoryResponseDto;
import com.himavincent.todo.category.dtos.CreateCategoryDto;
import com.himavincent.todo.category.dtos.UpdateCategoryDto;
import com.himavincent.todo.common.enums.DeleteCategoryMode;
import com.himavincent.todo.common.exception.BadRequestException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@Valid @RequestBody CreateCategoryDto data) {
        return categoryService.createCategory(data);
    }

    @PatchMapping("/{id}")
    public CategoryResponseDto updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryDto data) {
        return categoryService.updateCategory(id, data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id,
            @RequestParam String mode) {
        categoryService.deleteCategory(id, parseDeleteMode(mode));
    }

    private DeleteCategoryMode parseDeleteMode(String mode) {
        if (mode == null) {
            throw new BadRequestException("Delete mode is required");
        }
        switch (mode.toLowerCase()) {
            case "keep_tasks":
                return DeleteCategoryMode.KEEP_TASKS;
            case "delete_all_tasks":
                return DeleteCategoryMode.DELETE_ALL_TASKS;
            default:
                throw new BadRequestException("Invalid delete mode");
        }
    }
}
