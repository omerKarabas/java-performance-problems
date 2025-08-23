package com.example.demo.modules.cache.generatekey.timestampmismatch.api;

import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.response.OrderDTO;
import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.request.OrderSearchRequest;
import com.example.demo.modules.cache.generatekey.timestampmismatch.infrastructure.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cache/generatekey/timestampmismatch")
@RequiredArgsConstructor
@Slf4j
public class TimestampMismatchController {
    
    private final OrderService orderService;
    
    @GetMapping("/info")
    public ResponseEntity<String> getCacheTimestampMismatchInfo() {
        String info = """
            # Cache Key Timestamp Mismatch Problem
            
            ## Problem
            Using timestamps in cache keys creates inconsistent cache behavior.
            Each call generates different timestamp, causing cache misses.
            
            ## Demonstration
            1. PROBLEM: @Cacheable(key = "#request.generateCacheKeyWithTimestamp()") - Timestamp changes every call
            2. SOLUTION 1: @Cacheable(key = "#request.generateCacheKeyWithoutTimestamp()") - No timestamp
            3. SOLUTION 2: @Cacheable(key = "#request.generateTimeBucketedCacheKey()") - Day bucketed
            
            ## Test Endpoints
            - POST /api/cache/generatekey/timestampmismatch/orders/timestamp-problem
            - POST /api/cache/generatekey/timestampmismatch/orders/no-timestamp
            - POST /api/cache/generatekey/timestampmismatch/orders/time-bucketed

            - DELETE /api/cache/generatekey/timestampmismatch/orders/cache
            
            ## Expected Results
            - Problem: Every call hits database (no cache due to timestamp)
            - Solution 1: First call hits database, subsequent calls use cache
            - Solution 2: Cache works within same day (start of day 00:00:00)
            
            ## Testing
            1. Test data is auto-created via Flyway migration
            2. POST to problem endpoint twice - both hit database due to timestamp
            3. POST to solution endpoints twice - only first hits database
            4. Check logs for cache behavior and timestamp differences
            """;
        
        return ResponseEntity.ok(info);
    }
    
    @PostMapping("/orders/timestamp-problem")
    public ResponseEntity<String> demonstrateTimestampProblem(@RequestBody OrderSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<OrderDTO> orders = orderService.getOrdersByCustomerWithTimestampProblem(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "TIMESTAMP PROBLEM DEMONSTRATION\n" +
            "Customer: %s\n" +
            "Status: %s\n" +
            "Cache key with timestamp: %s\n" +
            "Orders found: %d\n" +
            "Response time: %d ms\n" +
            "Note: Check logs to see timestamp differences causing cache misses",
            request.getCustomerName(), request.getStatus(), 
            request.generateCacheKeyWithTimestamp(), orders.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/orders/no-timestamp")
    public ResponseEntity<String> demonstrateNoTimestampSolution(@RequestBody OrderSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<OrderDTO> orders = orderService.getOrdersByCustomerWithoutTimestamp(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "NO TIMESTAMP SOLUTION DEMONSTRATION\n" +
            "Customer: %s\n" +
            "Status: %s\n" +
            "Cache key without timestamp: %s\n" +
            "Orders found: %d\n" +
            "Response time: %d ms\n" +
            "Note: Check logs for cache hits with consistent keys",
            request.getCustomerName(), request.getStatus(), 
            request.generateCacheKeyWithoutTimestamp(), orders.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/orders/time-bucketed")
    public ResponseEntity<String> demonstrateTimeBucketedSolution(@RequestBody OrderSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<OrderDTO> orders = orderService.getOrdersByCustomerWithTimeBucketing(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "TIME-BUCKETED SOLUTION DEMONSTRATION\n" +
            "Customer: %s\n" +
            "Status: %s\n" +
            "Cache key with time bucket: %s\n" +
            "Orders found: %d\n" +
            "Response time: %d ms\n" +
            "Note: Cache works within same day (start of day 00:00:00)",
            request.getCustomerName(), request.getStatus(), 
            request.generateTimeBucketedCacheKey(), orders.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    

    
    @DeleteMapping("/orders/cache")
    public ResponseEntity<String> clearAllCaches() {
        orderService.clearAllCaches();
        return ResponseEntity.ok("All order caches cleared. Next calls will hit database.");
    }
}
