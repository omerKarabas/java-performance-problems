# Cache Key Consistent Ordering Problem

## Problem Description

HashSet'in sırası garanti olmadığı için cache key generation'da tutarsızlık problemi yaşanır. Aynı parametreler farklı sıralarda cache key'e dönüştürüldüklerinde farklı sonuçlar üretir.

### The Ordering Issue

HashSet kullanıldığında sıralama garanti değildir:

```java
// PROBLEM: Sıralama tutarsızlığı
Set<String> params = new HashSet<>();
params.add("category");
params.add("brand");
params.add("region");
String key1 = String.join("_", params);    // Result: "categorybrandregion"

Set<String> params2 = new HashSet<>();
params2.add("brand");
params2.add("category");
params2.add("region");
String key2 = String.join("_", params2);   // Result: "brandcategoryregion" - DIFFERENT!
```

Aynı parametreler farklı cache key'ler ürettiği için:
- Cache miss'ler gereksiz yere oluşur
- Aynı veri için farklı cache entry'leri oluşur
- Cache performansı düşer
- Inconsistent uygulama davranışı

## Solutions

### Solution 1: Manual Sorting

```java
// SOLUTION: Collections.sort() kullanarak sıralama
Set<String> params = new HashSet<>();
params.add("category");
params.add("brand");
params.add("region");

List<String> sortedParams = new ArrayList<>(params);
Collections.sort(sortedParams);
String key = String.join("_", sortedParams);    // Always: "brand_category_region"
```

### Solution 2: TreeSet Automatic Ordering

```java
// ALTERNATIVE: TreeSet otomatik sıralama yapar
Set<String> params = new HashSet<>();
params.add("category");
params.add("brand");
params.add("region");

TreeSet<String> sortedParams = new TreeSet<>(params);
String key = String.join("_", sortedParams);    // Always: "brand_category_region"
```

## Module Structure

```
consistentordering/
├── api/
│   ├── ConsistentOrderingController.java
│   └── model/
│       ├── dto/
│       │   ├── request/
│       │   │   └── ConsistentOrderingSearchRequest.java
│       │   └── response/
│       │       └── SearchParameterDTO.java
│       └── mapper/
│           └── SearchParameterMapper.java
├── domain/
│   └── entity/
│       └── SearchParameter.java
├── infrastructure/
│   ├── repository/
│   │   └── SearchParameterRepository.java
│   └── service/
│       ├── ConsistentOrderingService.java
│       └── impl/
│           └── ConsistentOrderingServiceImpl.java
└── Cache_Key_Consistent_Ordering_README.md
```

## API Endpoints

### Information Endpoint
- **GET** `/api/cache/generatekey/consistentordering/info`
  - Returns detailed information about the consistent ordering problem

### Problem Demonstration
- **POST** `/api/cache/generatekey/consistentordering/search/ordering-problem`
  - Demonstrates the ordering problem using HashSet without sorting

### Solutions
- **POST** `/api/cache/generatekey/consistentordering/search/sorted-solution`
  - Shows manual sorting solution using Collections.sort()
  
- **POST** `/api/cache/generatekey/consistentordering/search/treeset-solution`
  - Shows TreeSet automatic ordering solution

### Cache Management
- **DELETE** `/api/cache/generatekey/consistentordering/search/cache`
  - Clears all caches for fresh testing

## Testing the Problem

### Example Request Body

```json
{
  "category": "electronics",
  "brand": "samsung",
  "region": "europe",
  "minPrice": 100.00,
  "maxPrice": 1000.00,
  "minRating": 4
}
```

### Testing Scenarios

1. **Ordering Problem Test:**
   - Same parameters in request
   - HashSet might generate different keys each time
   - Inconsistent cache behavior

2. **Sorted Solutions:**
   - Manual sort: `Collections.sort(params)` ensures consistency
   - TreeSet: Automatic natural ordering
   - Same input always generates same cache key

## Expected Behavior

### Problem Endpoint
- Inconsistent cache keys for same parameters
- Potential cache misses
- Poor cache performance

### Solution Endpoints
- Consistent cache keys for same parameters
- Proper cache hits
- Optimal cache performance

## Key Takeaways

1. **Never use HashSet directly** for cache key generation without sorting
2. **Always sort parameters** before using them in cache keys
3. **Consider TreeSet** for automatic ordering
4. **Test cache key consistency** in different scenarios
5. **Document ordering strategy** for team understanding

## Common Ordering Strategies

- **Collections.sort()** - Manual sorting for full control
- **TreeSet** - Automatic natural ordering
- **Custom Comparator** - Domain-specific ordering rules
- **StringBuilder with fixed order** - Predefined parameter sequence

## Prevention Tips

- Use consistent key generation patterns across the application
- Create utility methods for common key patterns with sorting
- Validate key consistency in unit tests
- Consider using structured key builders with ordering
- Document ordering requirements in code reviews

## Performance Considerations

- Manual sorting: O(n log n) where n is parameter count
- TreeSet: O(n log n) for creation, O(log n) for each add
- For small parameter sets (< 10), performance difference is negligible
- Choose solution based on code readability and maintenance
