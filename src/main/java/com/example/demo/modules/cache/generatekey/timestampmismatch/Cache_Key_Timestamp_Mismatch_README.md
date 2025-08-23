# Cache Key Timestamp Mismatch Problem

This module demonstrates and solves the timestamp inconsistency problem when using timestamps in cache keys in Spring Cache.

## The Problem (Simple Explanation)

### What Happens
When you use timestamps in cache keys, Spring creates a new cache entry every time, even if the data is the same.

### Why It Happens
```java
@Cacheable(value = "orders", key = "#request.generateCacheKeyWithTimestamp()")  // PROBLEM
public List<OrderDTO> getOrders(OrderSearchRequest request) {
    // Every call generates different timestamp
    // Cache key becomes inconsistent between calls
}
```

**Example:**
- First call: `System.currentTimeMillis()` → 1734950400000
- Second call: `System.currentTimeMillis()` → 1734950400100
- Result: Cache miss every time, database query runs every time

### Performance Impact
- **Cache Miss**: Database query runs every time
- **Response Time**: Increases (100ms → 500ms+)
- **Resource Usage**: More CPU, memory, database connections
- **Throughput**: Application performance decreases

## The Solution (Simple Explanation)

### How to Fix It
Instead of using timestamps, use stable data or time-bucketing:

```java
// SOLUTION 1: No timestamp
@Cacheable(value = "orders", key = "#request.generateCacheKeyWithoutTimestamp()")
public List<OrderDTO> getOrders(OrderSearchRequest request) {
    // Uses consistent key based on actual data
    // Same data = same cache key = cache hit
}

// SOLUTION 2: Day-bucketed (start of day)
@Cacheable(value = "orders", key = "#request.generateTimeBucketedCacheKey()")
public List<OrderDTO> getOrders(OrderSearchRequest request) {
    // Uses day-based time buckets starting from 00:00:00
    // Cache works within same day
}
```

**How it works:**
- **No Timestamp**: `"customer:John Doe|status:COMPLETED"` → Always same
- **Day-Bucketed**: `"customer:John Doe|status:COMPLETED|day:1755907200"` → Same within day (00:00:00)

## Test Scenarios

### Test 1: Timestamp Cache Key Problem
```bash
# First call - Database query + cache storage
curl -X POST http://localhost:8080/api/cache/generatekey/timestampmismatch/orders/timestamp-problem \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John Doe", "status": "COMPLETED"}'

# Second call - Still hits database (no cache due to timestamp)
curl -X POST http://localhost:8080/api/cache/generatekey/timestampmismatch/orders/timestamp-problem \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John Doe", "status": "COMPLETED"}'
```

### Test 2: No Timestamp Solution
```bash
# First call - Database query + cache storage
curl -X POST http://localhost:8080/api/cache/generatekey/timestampmismatch/orders/no-timestamp \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John Doe", "status": "COMPLETED"}'

# Second call - Cache hit (fast response)
curl -X POST http://localhost:8080/api/cache/generatekey/timestampmismatch/orders/no-timestamp \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John Doe", "status": "COMPLETED"}'
```

### Test 3: Day-Bucketed Solution (Start of Day)
```bash
# First call - Database query + cache storage
curl -X POST http://localhost:8080/api/cache/generatekey/timestampmismatch/orders/time-bucketed \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John Doe", "status": "COMPLETED"}'

# Second call within same day - Cache hit
curl -X POST http://localhost:8080/api/cache/generatekey/timestampmismatch/orders/time-bucketed \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John Doe", "status": "COMPLETED"}'
```



## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cache/generatekey/timestampmismatch/info` | Problem explanation |
| POST | `/api/cache/generatekey/timestampmismatch/orders/timestamp-problem` | Timestamp problem demo |
| POST | `/api/cache/generatekey/timestampmismatch/orders/no-timestamp` | No timestamp solution demo |
| POST | `/api/cache/generatekey/timestampmismatch/orders/time-bucketed` | Day-bucketed solution demo |
| DELETE | `/api/cache/generatekey/timestampmismatch/orders/cache` | Clear all caches |

## Expected Results

| Scenario | First Call | Subsequent Calls | Improvement |
|----------|-------------|------------------|-------------|
| Timestamp Problem | 100ms | 100ms | 0% (no cache due to timestamp) |
| No Timestamp Solution | 100ms | 5ms | 95% (cache works) |
| Day-Bucketed Solution | 100ms | 5ms | 95% (cache works within day) |

## Best Practices

### Do's
- Use stable data for cache keys (customer name, status, etc.)
- Implement day-based bucketing for time-sensitive data
- Remove timestamps from cache keys
- Use day-based buckets (start of day 00:00:00) instead of millisecond precision

### Don'ts
- Don't use `System.currentTimeMillis()` in cache keys
- Don't use `LocalDateTime.now()` in cache keys
- Don't use `Instant.now()` in cache keys
- Don't use any time-based values that change frequently

## How to Test

1. **Start the application**
2. **POST to timestamp problem endpoint twice** with same request body - both will hit database
3. **POST to no timestamp solution endpoint twice** with same request body - only first hits database
4. **POST to time-bucketed solution endpoint twice** with same request body - only first hits database
5. **Check logs** to see cache behavior and timestamp differences
6. **Clear cache** and test again

### Sample Request Body
```json
{
  "customerName": "John Doe",
  "status": "COMPLETED"
}
```

## What We Demonstrate

- **Timestamp Problem**: `System.currentTimeMillis()` causes cache misses
- **No Timestamp Solution**: Stable cache keys for consistent caching
- **Day-Bucketed Solution**: Day-based caching starting from 00:00:00 for time-sensitive data
- **Performance Comparison**: Database hits vs cache hits
- **Logging**: Structured logs with cache key analysis

## Why It's Important

- **Real-world Issue**: Many developers accidentally use timestamps in cache keys
- **Performance Impact**: Cache becomes useless, database overload
- **Common Mistake**: Easy to make, hard to debug
- **Business Impact**: Slower response times, higher costs
- **Practical Solutions**: Day-based bucketing provides realistic alternatives to timestamp problems

---

**Note**: This module shows how timestamps can destroy cache effectiveness and provides practical solutions.
