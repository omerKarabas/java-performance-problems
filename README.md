# Java Performance Problems Demo

This project is created to demonstrate and solve common performance problems encountered in Java applications. Using Clean Architecture principles, each performance problem is addressed in a separate module.

## Topics Covered

### 1. N+1 Query Disaster
**Module**: `nplusone/`
**Documentation**: `N+1_Query_Disaster_README.md`
**Description**: Demonstrates and solves the N+1 performance problem in database queries.

**Future Modules to be Added**:
- Memory Leaks
- Thread Pool Issues
- Connection Pool Problems
- Cache Performance
- Database Indexing

## Architecture Structure

Each module follows Clean Architecture principles with this structure:

```
modules/
├── [module-name]/
│   ├── domain/              # Business logic entities
│   │   └── entity/         # Domain objects
│   ├── infrastructure/      # Data access and services
│   │   ├── repository/     # Data access layer
│   │   └── service/        # Business logic
│   │       ├── [ServiceName].java      # Service interfaces
│   │       └── impl/                   # Service implementations
│   └── api/                # REST API layer
│       ├── model/           # DTOs and mappers
│       │   ├── dto/         # Data Transfer Objects
│       │   └── mapper/      # Entity-DTO mappers
│       └── [ModuleName]Controller.java # REST endpoints
```

## Core Principles

- **Domain Layer**: Pure business logic, no external dependencies
- **Infrastructure Layer**: Data access and service implementations
- **API Layer**: REST endpoints and data transformation
- **Interface-Implementation Separation**: Services have interfaces and impl classes

## Module Requirements

Each module must provide:
- Method that demonstrates the problem
- Method that demonstrates the solution
- Performance comparison
- Clean Architecture implementation

## Required Technologies

### Runtime
- Java 17+
- Spring Boot 3.5.5

### Database
- Spring Data JPA
- H2 Database (development)
- Flyway migrations

### Build Tool
- Maven

### Logging
- SLF4J + Logback

### Code Quality
- Lombok (boilerplate reduction)

## Adding New Module

1. Create new folder under `modules/`
2. Follow Clean Architecture structure
3. Implement required layers
4. Write module-specific README

## References

### Clean Architecture
- [Clean Architecture by Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Spring Framework
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

### Database & Migration
- [Flyway Documentation](https://flywaydb.org/)
- [H2 Database](https://www.h2database.com/)

### Performance & Best Practices
- [Java Performance Tuning](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/)
- [Spring Performance Best Practices](https://spring.io/guides/gs/performance/)

---

**Note**: Each module has its own detailed documentation.
