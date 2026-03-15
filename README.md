# Todo Backend API

A clean, production-style REST API for managing tasks and categories.  
Built with **Spring Boot** and **MySQL**, following a layered architecture with DTOs, service-layer business logic, validation and global exception handling.

This project demonstrates how to design a backend that is **frontend-ready**, **maintainable** and **scalable**.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Jakarta Validation
- Springdoc OpenAPI (Swagger)
- Maven

---

## Project Purpose

The purpose of this project is to build a **cleanly structured backend API** for a Todo application.

The project focuses on:

- proper REST API design
- service-layer business logic
- DTO-based API responses
- global error handling
- soft delete vs hard delete strategies
- clean package structure
- Swagger-based API testing

This backend is designed to be connected to a **React frontend client**.

---

## Key Business Rules

### Entities

The system has two core entities:

- **Task**
- **Category**

### Relationships

- Many tasks belong to one category
- A category is optional for a task
- A task may have `null` category

### Task Deletion (Soft Delete)

Tasks are **not removed from the database**.

Instead:

```
archived = true
```

Normal task queries only return:

```
archived = false
```

### Category Deletion (Hard Delete)

Categories are deleted permanently, but require an explicit delete mode.

Two supported modes:

```
keep_tasks
delete_all_tasks
```

#### keep_tasks

- Category is deleted
- All tasks in that category have `category = null`

#### delete_all_tasks

- Tasks are soft deleted (`archived = true`)
- Category is deleted

---

## API Endpoints

### Category Endpoints

GET

```
/api/v1/categories
```

POST

```
/api/v1/categories
```

PATCH

```
/api/v1/categories/{id}
```

DELETE

```
/api/v1/categories/{id}?mode=keep_tasks
```

DELETE

```
/api/v1/categories/{id}?mode=delete_all_tasks
```

---

### Task Endpoints

GET

```
/api/v1/tasks
```

POST

```
/api/v1/tasks
```

PATCH

```
/api/v1/tasks/{id}
```

PATCH

```
/api/v1/tasks/{id}/completion
```

DELETE

```
/api/v1/tasks/{id}
```

---

## Project Structure

```
src/main/java/com/himavincent/todo
├── category
│   ├── dtos
│   │   ├── CreateCategoryDto
│   │   ├── UpdateCategoryDto
│   │   └── CategoryResponseDto
│   │
│   ├── entities
│   │   └── Category
│   │
│   ├── CategoryController
│   ├── CategoryRepository
│   └── CategoryService
│
├── task
│   ├── dtos
│   │   ├── CreateTaskDto
│   │   ├── UpdateTaskDto
│   │   ├── UpdateTaskCompletionDto
│   │   └── TaskResponseDto
│   │
│   ├── entities
│   │   └── Task
│   │
│   ├── TaskController
│   ├── TaskRepository
│   └── TaskService
│
├── common
│   ├── enums
│   │   └── DeleteCategoryMode
│   │
│   ├── exception
│   │   ├── HttpException
│   │   ├── BadRequestException
│   │   ├── NotFoundException
│   │   └── GlobalExceptionHandler
│   │
│   ├── dtos
│   │   └── ApiErrorResponse
│   │
│   └── BaseEntity
│
├── config
│   └── WebConfig
│
└── TodoApplication
```

---

## Running the Project

### 1. Clone the repository

```
git clone https://github.com/yourusername/todo-backend.git
```

### 2. Navigate into the project

```
cd todo-backend
```

### 3. Configure environment variables

Create a `.env` file with database credentials.

Example:

```
DB_URL=jdbc:mysql://localhost:3306/todo_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

---

### 4. Run the application

Using Maven wrapper:

```
./mvnw spring-boot:run
```

or on Windows:

```
mvnw spring-boot:run
```

---

## Swagger API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

Swagger allows testing all endpoints directly from the browser.

---

## Error Handling

The project includes **centralized global exception handling**.

Common responses:

Example error response:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Category not found",
  "path": "/api/v1/categories/10"
}
```

Handled errors include:

- Resource not found
- Duplicate resource creation
- Invalid input
- Invalid delete modes

---

## Design Goals

This project was designed with the following principles:

- clear separation of concerns
- DTO-based API contracts
- business logic in service layer
- clean controller responsibilities
- global exception handling
- scalable package structure

The architecture closely mirrors real-world backend systems used in production Spring Boot applications.

---

## Known Limitations

Current version intentionally excludes:

- authentication
- pagination
- backend-level sorting
- database migrations (Flyway)

These were excluded to keep the project focused on core backend architecture.

---

## Future Improvements

Possible future enhancements:

- pagination for task lists
- sorting and filtering
- authentication (JWT)
- Flyway database migrations
- Docker containerisation
- production deployment
- integration tests

---

## Learning Outcomes

This project demonstrates:

- REST API design with Spring Boot
- JPA entity modelling
- DTO mapping
- service-layer architecture
- global exception handling
- API testing with Swagger

---

## License

This project is released under the **MIT License**.
