# Cache Performance Problems Module

This module demonstrates and solves common cache performance problems encountered in Java applications. Each cache problem is addressed in a separate sub-module.

## Module Overview

The cache module focuses on identifying and solving performance issues related to caching in Spring applications. It provides practical examples and solutions for developers to understand and avoid common cache pitfalls.

## Current Sub-Modules

### 1. generatekey/hashcodemismatch
**Problem**: HashCode inconsistency when using objects as cache keys
**Description**: Each new object instance generates different hashCode, causing cache misses
**Solution**: Proper key generation using object fields instead of object instances

### 2. generatekey/timestampmismatch
**Problem**: Timestamp inconsistency when using timestamps in cache keys
**Description**: Each call generates different timestamp, causing cache misses
**Solution**: Timestamp-free keys or time-bucketed caching

## Future Sub-Modules to be Added

### generatekey/ category:
- **objectequality** - Object equality cache key problems
- **complexkey** - Complex object cache key issues
- **keycollision** - Cache key collision problems

### cache/ category:
- **timeout** - Cache timeout configuration issues
- **sizeoverflow** - Cache size overflow problems
- **ttlmisconfiguration** - TTL misconfiguration issues
- **eviction** - Cache eviction strategy problems
- **serialization** - Cache serialization issues

## What Each Sub-Module Contains

1. **Problem Demonstration** - Method that shows the performance issue
2. **Solution Implementation** - Method that demonstrates the fix
3. **Performance Comparison** - Problem vs solution metrics
4. **Clean Architecture** - Proper separation of concerns
5. **REST API Endpoints** - Easy testing and demonstration
6. **Comprehensive Documentation** - Problem explanation and solution guide

## Technical Requirements

- Spring Cache annotations (@Cacheable, @CacheEvict)
- Performance monitoring and logging
- Database integration (JPA/Hibernate)
- REST API endpoints
- Error handling

## Testing Approach

Each sub-module provides:
1. **Problem Simulation** - Scenario that triggers the performance issue
2. **Solution Verification** - Proof that the solution works
3. **Performance Metrics** - Response time and cache hit/miss ratios
4. **Real-world Examples** - Practical scenarios developers face

## How to Use This Module

1. **Start with the problem** - Understand what causes the performance issue
2. **See the solution** - Learn how to fix it properly
3. **Test the difference** - Compare performance before and after
4. **Apply to your code** - Use the same patterns in your applications

## Best Practices Demonstrated

- **Proper cache key generation** - Use primitive values or object fields
- **Cache configuration** - Let Spring Boot handle defaults
- **Performance monitoring** - Log and measure cache behavior
- **Clean code** - Follow best practices and patterns

---

**Note**: Each sub-module has its own detailed documentation with specific examples and test scenarios.
