# N+1 Query Disaster: Performance Anti-Pattern Demonstration

This module demonstrates and solves the **N+1 Query Problem**, one of the most common database performance anti-patterns, using Clean Architecture principles.

## What is N+1 Query Problem?

The N+1 problem is a common performance anti-pattern in database queries:

1. **First query**: fetches N main records (e.g., all users)
2. **Next N queries**: fetches related data for each main record separately

### Example Scenario

```
- 100 users exist
- First query: SELECT * FROM users (1 query)
- Next queries: SELECT * FROM orders WHERE user_id = X for each user (100 queries)
- TOTAL: 1 + 100 = 101 queries (N+1)
```

## Clean Architecture Structure

This module follows Clean Architecture principles with clear separation of concerns:

```
src/main/java/com/example/demo/modules/nplusone/
├── domain/                    # Domain Layer (Business Logic)
│   └── entity/               # Domain Entities
│       ├── User.java         # User domain entity
│       └── Order.java        # Order domain entity
├── infrastructure/            # Infrastructure Layer (Data Access)
│   ├── repository/           # Data Access Layer
│   │   ├── UserRepository.java    # User data access
│   │   └── OrderRepository.java   # Order data access
│   └── service/              # Business Logic Implementation
│       ├── UserService.java      # User service interface
│       ├── OrderService.java     # Order service interface
│       └── impl/                 # Service implementations
│           ├── UserServiceImpl.java  # User service implementation
│           └── OrderServiceImpl.java # Order service implementation
└── api/                      # API Layer (Presentation)
    ├── model/                # Data Transfer Objects
    │   ├── dto/             # Data Transfer Objects
    │   │   ├── UserDTO.java     # User DTO
    │   │   ├── OrderDTO.java    # Order DTO
    │   │   └── UserOrderDTO.java # Combined DTO
    │   └── mapper/          # Object Mappers
    │       └── UserMapper.java   # Entity to DTO mapper
    └── NPlusOneController.java   # REST API controller
```

## Architecture Benefits

### Clean Architecture Principles:
- **Domain Layer**: Pure business logic, no external dependencies
- **Infrastructure Layer**: Data access and external service implementations
- **API Layer**: Presentation and data transformation
- **Dependency Inversion**: High-level modules don't depend on low-level modules

### Design Patterns:
- **Repository Pattern**: Data access abstraction
- **Service Pattern**: Business logic encapsulation with interface-implementation separation
- **DTO Pattern**: Data transfer between layers
- **Mapper Pattern**: Entity to DTO transformation

## How to Run

### 1. Start Application
```bash
mvn spring-boot:run
```

### 2. Test Data Automatically Created
- 10 users with realistic data
- 3-6 orders per user (total ~40 orders)
- Data created via Flyway migration

### 3. Test N+1 Problem
```bash
# N+1 problem endpoint
curl http://localhost:8080/api/nplusone/users/nplusone

# Optimized solution
curl http://localhost:8080/api/nplusone/users/optimized
```

## API Endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /api/nplusone/users/nplusone` | Demonstrates N+1 problem |
| `GET /api/nplusone/users/optimized` | Shows optimized solution |
| `GET /api/nplusone/info` | N+1 problem explanation |

## N+1 Problem Demonstration

### Problem Scenario:
1. **STEP 1**: Fetch all users (1 query)
2. **STEP 2**: For each user, fetch orders individually (N queries)
3. **STEP 3**: Set orders to user entities
4. **Total**: 1 + N = N+1 queries

### Solution:
1. **STEP 1**: Use JOIN FETCH to get all data in single query
2. **Total**: 1 query

## Solution Methods

### 1. Using JOIN FETCH
```java
@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

### 2. Service Layer Separation
```java
// N+1 Problem: Each user triggers separate order query
List<Order> userOrders = orderService.getOrdersByUserId(user.getId());

// Optimized: Single query with JOIN FETCH
List<User> users = userRepository.findAllWithOrders();
```

### 3. Proper Lazy Loading Usage
```java
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
private List<Order> orders;
```

## Performance Comparison

| Approach | Query Count | Performance | Architecture |
|----------|-------------|-------------|--------------|
| N+1 Problem | 1 + N | Poor | Service-based individual queries |
| JOIN FETCH | 1 | Good | Repository-based bulk fetch |
| EntityGraph | 1 | Good | JPA-based optimization |

## Database Migration

Uses Flyway for database schema management:

```sql
-- V1__Create_N_Plus_One_Query_Disaster_Tables.sql
-- Creates tables and test data for demonstration
```

## Technical Stack

- **Java 17** with Spring Boot 3.5.5
- **Clean Architecture** with layered design
- **Spring Data JPA** with H2 Database
- **Flyway** for database migrations
- **Lombok** for clean code
- **SLF4J** for proper logging

## Learning Objectives

With this module you will learn:

1. **N+1 problem identification** and real-world demonstration
2. **Clean Architecture** implementation
3. **Service layer design** and separation of concerns
4. **Repository pattern** usage
5. **Performance optimization** techniques
6. **Database migration** management

## Next Steps

After completing this module:

1. **Memory Leaks** module
2. **Thread Pool Exhaustion** module
3. **Database Connection Pool** module
4. **Cache Performance** module

---

**Note**: This module demonstrates enterprise-level architecture patterns. Use as reference for production applications.
