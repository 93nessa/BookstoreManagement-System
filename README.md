# BookstoreManagement-System


---

```markdown

A simple Spring Boot REST API for managing bookstore inventory.
## Objectives

- Learn Spring Boot fundamentals  
- Practice building REST APIs  
- Understand JPA/Hibernate  
- Connect Java applications to MySQL  
- Build a functional CRUD backend  

---

## Installation & Setup

### Requirements
- Java 17+
- MySQL Server
- Maven
- VS Code or IntelliJ

## Features
- Add books  
- View all books  
- Update books  
- Delete books  
- MySQL database integration  

## Run Instructions
1. Create MySQL database: `CREATE DATABASE bookstore;`
2. Update `application.properties` with your MySQL username/password.
3. Run the app: `./mvnw spring-boot:run`

## Endpoints
- GET `/books`
- GET `/books/{id}`
- POST `/books`
- PUT `/books/{id}`
- DELETE `/books/{id}`
