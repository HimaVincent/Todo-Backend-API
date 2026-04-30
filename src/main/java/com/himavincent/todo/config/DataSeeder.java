package com.himavincent.todo.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.himavincent.todo.category.CategoryRepository;
import com.himavincent.todo.category.entities.Category;
import com.himavincent.todo.task.TaskRepository;
import com.himavincent.todo.task.entities.Task;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            CategoryRepository categoryRepository,
            TaskRepository taskRepository) {
        return args -> {
            if (categoryRepository.count() > 0 || taskRepository.count() > 0) {
                return;
            }

            Category work = categoryRepository.save(new Category("Work"));
            Category personal = categoryRepository.save(new Category("Personal"));
            Category shopping = categoryRepository.save(new Category("Shopping"));

            LocalDateTime now = LocalDateTime.now();

            LocalDateTime todayMorning = now.withHour(10).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime todayEvening = now.withHour(18).withMinute(0).withSecond(0).withNano(0);

            LocalDateTime yesterday = now.minusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime twoDaysAgo = now.minusDays(2).withHour(14).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime threeDaysAgo = now.minusDays(3).withHour(11).withMinute(0).withSecond(0).withNano(0);

            LocalDateTime tomorrow = now.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime nextWeek = now.plusDays(7).withHour(9).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime nextTwoWeeks = now.plusDays(14).withHour(15).withMinute(0).withSecond(0).withNano(0);

            // TODAY
            taskRepository.save(new Task(
                    "Finish frontend filters", "Hook filter cards", todayMorning, false, false, work));
            taskRepository.save(new Task(
                    "Fix styling issues", "Check spacing + alignment", todayEvening, false, false, work));
            taskRepository.save(new Task(
                    "Call bank", "Clarify charges", todayMorning, false, false, personal));

            // OVERDUE
            taskRepository.save(new Task(
                    "Book flights", "Check prices", yesterday, false, false, personal));
            taskRepository.save(new Task(
                    "Submit tax form", "Due last week", twoDaysAgo, false, false, work));
            taskRepository.save(new Task(
                    "Renew subscription", "Missed reminder", threeDaysAgo, false, false, personal));
            taskRepository.save(new Task(
                    "Pay electricity bill", "Late fee risk", yesterday, false, false, shopping));

            // SCHEDULED
            taskRepository.save(new Task(
                    "Plan weekend trip", "Check locations", tomorrow, false, false, personal));
            taskRepository.save(new Task(
                    "Gym session", "Leg day", nextWeek, false, false, personal));
            taskRepository.save(new Task(
                    "Doctor appointment", "Annual checkup", nextWeek, false, false, personal));
            taskRepository.save(new Task(
                    "Prepare presentation", "Slides for meeting", nextTwoWeeks, false, false, work));

            // UNSCHEDULED
            taskRepository.save(new Task(
                    "Read book", "Atomic Habits", null, false, false, personal));
            taskRepository.save(new Task(
                    "Organise wardrobe", "Declutter clothes", null, false, false, personal));

            // COMPLETED
            taskRepository.save(new Task(
                    "Buy groceries", "Milk, eggs, bread", yesterday, true, false, shopping));
            taskRepository.save(new Task(
                    "Submit report", "Weekly report", threeDaysAgo, true, false, work));

            // UNCATEGORISED
            taskRepository.save(new Task(
                    "Brainstorm app ideas", "Think of new project", null, false, false, null));

            taskRepository.save(new Task(
                    "Clean desktop files", "Remove unused downloads", null, false, false, null));

            taskRepository.save(new Task(
                    "Watch design inspiration", "Dribbble + Behance", tomorrow, false, false, null));

            taskRepository.save(new Task(
                    "Meditation session", "10 minutes", yesterday, true, false, null));
        };
    }
}