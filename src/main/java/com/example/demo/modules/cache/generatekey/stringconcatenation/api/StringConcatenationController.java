package com.example.demo.modules.cache.generatekey.stringconcatenation.api;

import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.response.StringConcatDTO;
import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.request.StringConcatSearchRequest;
import com.example.demo.modules.cache.generatekey.stringconcatenation.infrastructure.service.StringConcatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cache/generatekey/stringconcatenation")
@RequiredArgsConstructor
@Slf4j
public class StringConcatenationController {
    
    private final StringConcatService stringConcatService;
    
    @GetMapping("/info")
    public ResponseEntity<String> getCacheStringConcatenationInfo() {
        String info = """
            # Cache Key String Concatenation Collision Problem
            
            ## Problem
            String concatenation without delimiters creates collision risks.
            Different input combinations can generate identical cache keys.
            
            ## Demonstration
            1. PROBLEM: userId + productId = collision risk
            2. SOLUTION 1: userId + ":" + productId = safe delimiter
            3. SOLUTION 2: "userproduct:" + userId + ":" + productId = hash prefix
            
            ## Test Endpoints
            - POST /api/cache/generatekey/stringconcatenation/userproducts/collision-problem
            - POST /api/cache/generatekey/stringconcatenation/userproducts/safe-delimiter
            - POST /api/cache/generatekey/stringconcatenation/userproducts/hash-prefix
            - DELETE /api/cache/generatekey/stringconcatenation/userproducts/cache
            
            ## Expected Results
            - Problem: "user123" + "product45" = "user123product45"
                      "user12" + "3product45" = "user123product45" (COLLISION!)
            - Solution 1: "user123:product45" != "user12:3product45" (SAFE)
            - Solution 2: "userproduct:user123:product45" (UNIQUE)
            
            ## Testing
            1. Test data is auto-created via Flyway migration
            2. POST to problem endpoint with different user/product combinations
            3. POST to solution endpoints to see cache behavior
            4. Check logs for cache hits and key generation
            """;
        
        return ResponseEntity.ok(info);
    }
    
    @PostMapping("/userproducts/collision-problem")
    public ResponseEntity<String> demonstrateCollisionProblem(@RequestBody StringConcatSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<StringConcatDTO> userProducts = stringConcatService.getUserProductsWithCollisionProblem(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "STRING CONCATENATION COLLISION PROBLEM\n" +
            "User ID: %s\n" +
            "Product ID: %s\n" +
            "Problematic cache key: '%s'\n" +
            "User products found: %d\n" +
            "Response time: %d ms\n" +
            "WARNING: This key generation can cause collisions!\n" +
            "Example: user123+product45 = user123product45\n" +
            "         user12+3product45 = user123product45 (SAME KEY!)",
            request.getUserId(), request.getProductId(), 
            request.generateProblematicCacheKey(), userProducts.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/userproducts/safe-delimiter")
    public ResponseEntity<String> demonstrateSafeDelimiterSolution(@RequestBody StringConcatSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<StringConcatDTO> userProducts = stringConcatService.getUserProductsWithSafeCacheKey(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "SAFE DELIMITER SOLUTION\n" +
            "User ID: %s\n" +
            "Product ID: %s\n" +
            "Safe cache key: '%s'\n" +
            "User products found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: Using delimiter ':' prevents collisions!\n" +
            "Example: user123:product45 != user12:3product45",
            request.getUserId(), request.getProductId(), 
            request.generateSafeCacheKey(), userProducts.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/userproducts/hash-prefix")
    public ResponseEntity<String> demonstrateHashPrefixSolution(@RequestBody StringConcatSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<StringConcatDTO> userProducts = stringConcatService.getUserProductsWithHashBasedCacheKey(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "HASH PREFIX SOLUTION\n" +
            "User ID: %s\n" +
            "Product ID: %s\n" +
            "Hash-based cache key: '%s'\n" +
            "User products found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: Using prefix and delimiters ensures uniqueness!\n" +
            "Example: userproduct:user123:product45",
            request.getUserId(), request.getProductId(), 
            request.generateHashBasedCacheKey(), userProducts.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/userproducts/cache")
    public ResponseEntity<String> clearAllCaches() {
        stringConcatService.clearAllCaches();
        return ResponseEntity.ok("All string concatenation caches cleared. Next calls will hit database.");
    }
}
