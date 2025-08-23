# Cache Key String Concatenation Collision Problem

## Problem Description

String concatenation without proper delimiters creates collision risks in cache key generation. Different input combinations can generate identical cache keys, leading to incorrect cache behavior and data retrieval issues.

### The Collision Issue

When concatenating strings directly without delimiters:

```java
// PROBLEM: Collision risk
String key1 = "user123" + "product45";    // Result: "user123product45"
String key2 = "user12" + "3product45";    // Result: "user123product45" - SAME KEY!
```

Both different input combinations produce the same cache key, causing:
- Cache hits when they shouldn't occur
- Wrong data being returned from cache
- Inconsistent application behavior

## Solutions

### Solution 1: Use Delimiters

```java
// SOLUTION: Use delimiter to separate components
String key = userId + ":" + productId;    // "user123:product45" vs "user12:3product45"
```

### Solution 2: Use Hash Prefix

```java
// ALTERNATIVE: Use meaningful prefix with delimiters
String key = "userproduct:" + userId + ":" + productId;    // "userproduct:user123:product45"
```

## Module Structure

```
stringconcatenation/
├── api/
│   ├── StringConcatenationController.java
│   └── model/
│       ├── dto/
│       │   ├── request/
│       │   │   └── StringConcatSearchRequest.java
│       │   └── response/
│       │       └── StringConcatDTO.java
│       └── mapper/
│           └── StringConcatMapper.java
├── domain/
│   └── entity/
│       └── StringConcatCache.java
├── infrastructure/
│   ├── repository/
│   │   └── StringConcatRepository.java
│   └── service/
│       ├── StringConcatService.java
│       └── impl/
│           └── StringConcatServiceImpl.java
└── Cache_Key_String_Concatenation_Collision_README.md
```

## API Endpoints

### Information Endpoint
- **GET** `/api/cache/generatekey/stringconcatenation/info`
  - Returns detailed information about the collision problem

### Problem Demonstration
- **POST** `/api/cache/generatekey/stringconcatenation/userproducts/collision-problem`
  - Demonstrates the collision problem using problematic key generation

### Solutions
- **POST** `/api/cache/generatekey/stringconcatenation/userproducts/safe-delimiter`
  - Shows safe delimiter-based solution
  
- **POST** `/api/cache/generatekey/stringconcatenation/userproducts/hash-prefix`
  - Shows hash prefix-based solution

### Cache Management
- **DELETE** `/api/cache/generatekey/stringconcatenation/userproducts/cache`
  - Clears all caches for fresh testing

## Testing the Problem

### Example Request Body

```json
{
  "userId": "user123",
  "productId": "product45"
}
```

### Testing Scenarios

1. **Collision Test:**
   - Request 1: `{"userId": "user123", "productId": "product45"}`
   - Request 2: `{"userId": "user12", "productId": "3product45"}`
   - Both generate the same problematic key: `user123product45`

2. **Safe Solutions:**
   - Delimiter: `user123:product45` vs `user12:3product45` (Different!)
   - Hash prefix: `userproduct:user123:product45` vs `userproduct:user12:3product45` (Different!)

## Expected Behavior

### Problem Endpoint
- Same cache key for different inputs
- Potential wrong data retrieval
- Inconsistent cache behavior

### Solution Endpoints
- Unique cache keys for different inputs
- Correct cache behavior
- Consistent data retrieval

## Key Takeaways

1. **Never concatenate strings directly** for cache keys
2. **Always use delimiters** or prefixes to separate components
3. **Test edge cases** where inputs might create collisions
4. **Consider the domain** when choosing delimiter characters
5. **Document key generation strategy** for team understanding

## Common Delimiter Strategies

- **Colon (`:`)** - Most common, readable
- **Pipe (`|`)** - Good when colons might appear in data
- **Hash (`#`)** - URL-safe alternative
- **Custom prefix** - Domain-specific namespacing

## Prevention Tips

- Use consistent key generation patterns across the application
- Create utility methods for common key patterns
- Validate key uniqueness in tests
- Consider using structured key builders
- Document collision risks in code reviews
