package com.example.demo.modules.cache.generatekey.nullvalidation.api;

import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.response.NullValidationDTO;
import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.request.NullValidationSearchRequest;
import com.example.demo.modules.cache.generatekey.nullvalidation.infrastructure.service.NullValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cache/generatekey/nullvalidation")
@RequiredArgsConstructor
@Slf4j
public class NullValidationController {
    
    private final NullValidationService nullValidationService;
    
    @GetMapping("/info")
    public ResponseEntity<String> getCacheNullValidationInfo() {
        String info = """
            # Cache Key Null Validation Problem
            
            ## Problem
            Not performing null value checks when generating cache keys can lead to serious issues.
            When null values are concatenated with strings, it results in cache keys containing "null" strings,
            which can cause runtime exceptions and inconsistent behavior.
            
            ## Demonstration
            1. PROBLEM: String key = userId + "_" + productId; // Can result in "null_123"
            2. SOLUTION: String key = (userId != null ? userId : "guest") + "_" + productId;
            
            ## Test Endpoints
            - POST /api/cache/generatekey/nullvalidation/search/null-problem
            - POST /api/cache/generatekey/nullvalidation/search/safe-validation
            - POST /api/cache/generatekey/nullvalidation/search/optional-validation
            - POST /api/cache/generatekey/nullvalidation/search/utility-validation
            - DELETE /api/cache/generatekey/nullvalidation/cache
            
            ## Expected Results
            - Problem: Cache keys contain "null" strings, potential runtime exceptions
            - Solutions: Clean cache keys without "null" strings, safe null handling
            
            ## Common Null Value Scenarios
            - User ID from authentication (can be null for guest users)
            - Optional search filters and query parameters
            - Nullable database fields and foreign keys
            - External API responses that might return null
            
            ## Safe Null Handling Strategies
            1. Ternary Operator: (value != null ? value : "default")
            2. Optional Class: Optional.ofNullable(value).orElse("default")
            3. Utility Methods: safeString(value, "default")
            4. Apache Commons: StringUtils.defaultIfBlank(value, "default")
            
            ## Testing
            1. Test data is auto-created via Flyway migration
            2. POST to endpoints with null userId/categoryId values
            3. Observe cache behavior and key generation strategies
            4. Check logs for cache hits/misses and null handling warnings
            
            ## Key Benefits
            - No runtime exceptions from null values
            - Consistent cache keys without "null" strings
            - Predictable behavior across different scenarios
            - Better user experience for guest/anonymous users
            """;
        
        return ResponseEntity.ok(info);
    }
    
    @PostMapping("/search/null-problem")
    public ResponseEntity<String> demonstrateNullValidationProblem(@RequestBody NullValidationSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<NullValidationDTO> results = nullValidationService.searchWithNullValidationProblem(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "NULL VALIDATION CACHE KEY PROBLEM\n" +
            "User ID: %s\n" +
            "Product ID: %s\n" +
            "Category ID: %s\n" +
            "Problematic cache key: '%s'\n" +
            "Results found: %d\n" +
            "Response time: %d ms\n" +
            "WARNING: Cache key contains 'null' strings!\n" +
            "Problem: userId + \"_\" + productId can result in 'null_123'\n" +
            "Impact: Inconsistent cache keys, potential runtime exceptions",
            request.getUserId(), request.getProductId(), request.getCategoryId(),
            request.generateProblematicNullCacheKey(), results.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/search/safe-validation")
    public ResponseEntity<String> demonstrateSafeNullValidation(@RequestBody NullValidationSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<NullValidationDTO> results = nullValidationService.searchWithSafeNullValidation(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "SAFE NULL VALIDATION SOLUTION - TERNARY OPERATOR\n" +
            "User ID: %s\n" +
            "Product ID: %s\n" +
            "Category ID: %s\n" +
            "Safe cache key: '%s'\n" +
            "Results found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: Using ternary operator for null validation!\n" +
            "Benefit: Clean cache keys without 'null' strings\n" +
            "Approach: String safeUserId = (userId != null ? userId : \"guest\");",
            request.getUserId(), request.getProductId(), request.getCategoryId(),
            request.generateSafeNullValidatedCacheKey(), results.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/search/optional-validation")
    public ResponseEntity<String> demonstrateOptionalValidation(@RequestBody NullValidationSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<NullValidationDTO> results = nullValidationService.searchWithOptionalBasedValidation(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "OPTIONAL-BASED NULL VALIDATION SOLUTION\n" +
            "User ID: %s\n" +
            "Product ID: %s\n" +
            "Category ID: %s\n" +
            "Optional cache key: '%s'\n" +
            "Results found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: Using Optional for type-safe null handling!\n" +
            "Benefit: Type-safe null handling with default values\n" +
            "Approach: Optional.ofNullable(userId).orElse(\"guest\");",
            request.getUserId(), request.getProductId(), request.getCategoryId(),
            request.generateOptionalBasedCacheKey(), results.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    

    
    @DeleteMapping("/cache")
    public ResponseEntity<String> clearAllCaches() {
        nullValidationService.clearAllCaches();
        return ResponseEntity.ok("All null validation caches cleared. Next calls will hit database.");
    }
}
