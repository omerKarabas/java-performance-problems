package com.example.demo.modules.cache.generatekey.hashcodemismatch.api;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.response.ProductDTO;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.request.ProductSearchRequest;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.ProductCategory;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cache/generatekey/hashcodemismatch")
@RequiredArgsConstructor
@Slf4j
public class HashCodeMismatchController {
    
    private final ProductService productService;
    
    @GetMapping("/info")
    public ResponseEntity<String> getCacheHashCodeMismatchInfo() {
        String info = """
            # Cache Key HashCode Mismatch Problem
            
            ## Problem
            Using objects as cache keys creates inconsistent hashCode values.
            Each new object instance generates different hashCode, causing cache misses.
            
            ## Demonstration
            1. PROBLEM: @Cacheable(key = "#request.hashCode()") - Request object hashCode changes
            2. SOLUTION: @Cacheable(key = "#category") - String key consistent
            
            ## Test Endpoints
            - POST /api/cache/generatekey/hashcodemismatch/products/problem
            - POST /api/cache/generatekey/hashcodemismatch/products/solution
            - DELETE /api/cache/generatekey/hashcodemismatch/products/cache
            
            ## Expected Results
            - Problem: Every call hits database (no cache)
            - Solution: First call hits database, subsequent calls use cache
            
            ## Testing
            1. Test data is auto-created via Flyway migration
            2. POST to problem endpoint twice with same request body - both hit database
            3. POST to solution endpoint twice with same request body - only first hits database
            4. Check logs for cache behavior
            """;
        
        return ResponseEntity.ok(info);
    }
    
    @PostMapping("/products/problem")
    public ResponseEntity<String> demonstrateProblem(@RequestBody ProductSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<ProductDTO> products = productService.getProductsByCategoryWithProblem(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "PROBLEM DEMONSTRATION\n" +
            "Category: %s\n" +
            "Request object hashCode: %d\n" +
            "Products found: %d\n" +
            "Response time: %d ms\n" +
            "Note: Check logs to see hashCode differences",
            request.getCategory(), request.hashCode(), products.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/products/solution")
    public ResponseEntity<String> demonstrateSolution(@RequestBody ProductSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<ProductDTO> products = productService.getProductsByCategoryWithSolution(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "SOLUTION DEMONSTRATION\n" +
            "Category: %s\n" +
            "Request object hashCode: %d\n" +
            "Generated cache key: %s\n" +
            "Products found: %d\n" +
            "Response time: %d ms\n" +
            "Note: Check logs for cache hits",
            request.getCategory(), request.hashCode(), request.generateCacheKey(), products.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    

    
    @DeleteMapping("/products/cache")
    public ResponseEntity<String> clearAllCaches() {
        productService.clearAllCaches();
        return ResponseEntity.ok("All caches cleared. Next calls will hit database.");
    }
}
