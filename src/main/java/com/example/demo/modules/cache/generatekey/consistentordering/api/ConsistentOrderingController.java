package com.example.demo.modules.cache.generatekey.consistentordering.api;

import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.response.SearchParameterDTO;
import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.request.ConsistentOrderingSearchRequest;
import com.example.demo.modules.cache.generatekey.consistentordering.infrastructure.service.ConsistentOrderingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cache/generatekey/consistentordering")
@RequiredArgsConstructor
@Slf4j
public class ConsistentOrderingController {
    
    private final ConsistentOrderingService consistentOrderingService;
    
    @GetMapping("/info")
    public ResponseEntity<String> getCacheConsistentOrderingInfo() {
        String info = """
            # Cache Key Consistent Ordering Problem
            
            ## Problem
            Since HashSet doesn't guarantee ordering, cache keys become inconsistent.
            Same parameters converted to cache keys in different orders produce different results.
            
            ## Demonstration
            1. PROBLEM: Set<String> params = new HashSet<>(); // order not guaranteed
                       String key = String.join("_", params);
            2. SOLUTION 1: List<String> sortedParams = new ArrayList<>(params);
                          Collections.sort(sortedParams);
                          String key = String.join("_", sortedParams);
            3. SOLUTION 2: TreeSet<String> sortedParams = new TreeSet<>(params); // automatic ordering
            
            ## Test Endpoints
            - POST /api/cache/generatekey/consistentordering/search/ordering-problem
            - POST /api/cache/generatekey/consistentordering/search/sorted-solution
            - POST /api/cache/generatekey/consistentordering/search/treeset-solution
            - DELETE /api/cache/generatekey/consistentordering/search/cache
            
            ## Expected Results
            - Problem: Set["category", "brand", "region"] might become: categorybrandregion
                      Set["brand", "category", "region"] might become: brandcategoryregion (DIFFERENT!)
            - Solution 1: Always sorted: brand_category_region (CONSISTENT)
            - Solution 2: TreeSet auto-sort: brand_category_region (CONSISTENT)
            
            ## Testing
            1. Test data is auto-created via Flyway migration
            2. POST to problem endpoint with different parameter orders
            3. POST to solution endpoints to see consistent cache behavior
            4. Check logs for cache hits and key generation
            """;
        
        return ResponseEntity.ok(info);
    }
    
    @PostMapping("/search/ordering-problem")
    public ResponseEntity<String> demonstrateOrderingProblem(@RequestBody ConsistentOrderingSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<SearchParameterDTO> searchResults = consistentOrderingService.getSearchResultsWithOrderingProblem(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "CONSISTENT ORDERING PROBLEM\n" +
            "Search Parameters: %s\n" +
            "Problematic cache key: '%s'\n" +
            "Search results found: %d\n" +
            "Response time: %d ms\n" +
            "WARNING: This key generation can be inconsistent!\n" +
            "Example: Set[category, brand, region] might become: categorybrandregion\n" +
            "         Set[brand, category, region] might become: brandcategoryregion (DIFFERENT!)",
            request.getSearchParams(), request.generateProblematicCacheKey(), 
            searchResults.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/search/sorted-solution")
    public ResponseEntity<String> demonstrateSortedSolution(@RequestBody ConsistentOrderingSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<SearchParameterDTO> searchResults = consistentOrderingService.getSearchResultsWithConsistentOrdering(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "SORTED SOLUTION\n" +
            "Search Parameters: %s\n" +
            "Sorted cache key: '%s'\n" +
            "Search results found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: Using Collections.sort() ensures consistent ordering!\n" +
            "Example: Always sorted: brand_category_region (CONSISTENT)",
            request.getSearchParams(), request.generateSortedCacheKey(), 
            searchResults.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/search/treeset-solution")
    public ResponseEntity<String> demonstrateTreeSetSolution(@RequestBody ConsistentOrderingSearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<SearchParameterDTO> searchResults = consistentOrderingService.getSearchResultsWithTreeSetOrdering(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "TREESET SOLUTION\n" +
            "Search Parameters: %s\n" +
            "TreeSet cache key: '%s'\n" +
            "Search results found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: TreeSet automatically maintains natural ordering!\n" +
            "Example: TreeSet auto-sorts: brand_category_region (CONSISTENT)",
            request.getSearchParams(), request.generateTreeSetCacheKey(), 
            searchResults.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/search/cache")
    public ResponseEntity<String> clearAllCaches() {
        consistentOrderingService.clearAllCaches();
        return ResponseEntity.ok("All consistent ordering caches cleared. Next calls will hit database.");
    }
}
