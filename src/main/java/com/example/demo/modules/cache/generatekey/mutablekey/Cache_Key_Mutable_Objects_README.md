# Cache Key Mutable Objects Problem

## Problem Description

Using mutable objects (like `List`, `Set`, `Map`) as cache keys creates serious performance and consistency issues. When a mutable object is modified after being used as a cache key, the cache system can no longer locate the cached value, leading to cache misses and degraded performance.

### The Mutable Key Issue

When using mutable objects as cache keys:

```java
// PROBLEM: Mutable List as cache key
List<String> keyList = new ArrayList<>();
keyList.add("item1");
cache.put(keyList, value);   // Cache stores with this List as key

// Later in code...
keyList.add("item2");        // List is modified!
Object cached = cache.get(keyList);  // CACHE MISS! Key changed!
```

The problem occurs because:
- The object's hash code changes when its content changes
- Cache lookup fails because the key is no longer equivalent
- Application performance degrades due to unexpected cache misses
- Inconsistent behavior that's hard to debug

## Solutions

### Solution: Convert to Immutable String

```java
// SOLUTION: Convert mutable List to immutable String
List<String> keyList = Arrays.asList("item1", "item2");
String key = String.join(":", keyList);    // "item1:item2"
cache.put(key, value);       // Immutable string key
// Even if original List changes, cache key remains stable
```



## Module Structure

```
mutablekey/
├── api/
│   ├── MutableKeyController.java
│   └── model/
│       ├── dto/
│       │   ├── request/
│       │   │   └── MutableKeySearchRequest.java
│       │   └── response/
│       │       └── MutableKeyDTO.java
│       └── mapper/
│           └── MutableKeyMapper.java
├── domain/
│   └── entity/
│       └── MutableKeyCache.java
├── infrastructure/
│   ├── repository/
│   │   └── MutableKeyRepository.java
│   └── service/
│       ├── MutableKeyService.java
│       └── impl/
│           └── MutableKeyServiceImpl.java
└── Cache_Key_Mutable_Objects_README.md
```

## API Endpoints

### Information Endpoint
- **GET** `/api/cache/generatekey/mutablekey/info`
  - Returns detailed information about the mutable key problem

### Problem Demonstration
- **POST** `/api/cache/generatekey/mutablekey/search/mutable-problem`
  - Demonstrates the problem using mutable List objects as cache keys

### Solutions
- **POST** `/api/cache/generatekey/mutablekey/search/safe-string`
  - Shows safe string-based solution using `String.join()`



### Cache Management
- **DELETE** `/api/cache/generatekey/mutablekey/cache`
  - Clears all caches for fresh testing

## Testing the Problem

### Example Request Body

```json
{
  "categories": ["electronics", "computers"],
  "tags": ["laptop", "gaming"]
}
```

### Testing Scenarios

1. **Mutable Key Problem:**
   - Use the same request multiple times
   - Observe how List modifications affect cache behavior
   - Check logs for cache misses

2. **Safe Solution:**
   - String solution: `electronics:computers:laptop:gaming`
   - Creates stable, immutable cache keys

## Expected Behavior

### Problem Endpoint
- Unpredictable cache behavior
- Cache misses when keys are modified
- Performance degradation
- Difficult debugging

### Solution Endpoints
- Consistent cache hits
- Stable key generation
- Predictable performance
- Easy to debug and trace

## Common Mutable Objects to Avoid

### Collections
- `ArrayList`, `LinkedList`, `Vector`
- `HashSet`, `TreeSet`, `LinkedHashSet`
- `HashMap`, `TreeMap`, `LinkedHashMap`

### Arrays
- `String[]`, `Object[]`, `int[]`
- Any mutable array types

### Custom Objects
- Any object with mutable fields
- Objects without proper `equals()` and `hashCode()`

## Safe Key Alternatives

### Immutable Types
- `String` (immutable)
- Primitive wrappers (`Integer`, `Long`, `Boolean`)
- `BigDecimal`, `BigInteger`
- `UUID`

### Immutable Collections
- `List.of()`, `Set.of()`, `Map.of()`
- Google Guava immutable collections
- Apache Commons immutable collections

### Records and Value Objects
```java
// Safe: Record with immutable fields
public record CacheKey(String category, String tag) {}

// Safe: Immutable value object
public final class SearchKey {
    private final String prefix;
    private final List<String> values;  // Make defensive copy!
    
    public SearchKey(String prefix, List<String> values) {
        this.prefix = prefix;
        this.values = List.copyOf(values);  // Immutable copy
    }
}
```

## Prevention Tips

1. **Use immutable objects** as cache keys whenever possible
2. **Convert mutable collections** to strings using delimiters
3. **Create defensive copies** when working with collections
4. **Implement proper equals() and hashCode()** for custom key objects
5. **Document key generation strategy** for team understanding
6. **Test cache behavior** with key modifications in unit tests
7. **Use static analysis tools** to detect mutable key usage

## Performance Impact

### Problem Scenarios
- Cache hit ratio drops significantly
- Increased database/service calls
- Higher memory usage due to cache bloat
- Unpredictable response times

### Solution Benefits
- Consistent cache hit ratios
- Reduced database load
- Predictable performance
- Easier monitoring and debugging

## Debugging Tips

1. **Log cache keys** to verify consistency
2. **Monitor cache hit ratios** for unexpected drops
3. **Use cache statistics** to identify problematic patterns
4. **Add assertions** in development to catch mutable key usage
5. **Review code** for collections used as cache keys

## Key Takeaways

1. **Never use mutable objects** as cache keys
2. **Convert collections to strings** using appropriate delimiters
3. **Use immutable objects** or primitive types for keys
4. **Test key consistency** across object modifications
5. **Document caching strategy** clearly for maintainability
6. **Monitor cache performance** to detect issues early
7. **Educate team members** about mutable key risks
