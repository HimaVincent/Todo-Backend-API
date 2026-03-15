package com.himavincent.todo.task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himavincent.todo.task.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByArchivedFalse();

    Optional<Task> findByIdAndArchivedFalse(Long id);

    List<Task> findByCategoryIdAndArchivedFalse(Long categoryId);

    List<Task> findByCategoryId(Long categoryId);

}