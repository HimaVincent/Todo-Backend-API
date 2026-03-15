package com.himavincent.todo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import com.himavincent.todo.category.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
}
