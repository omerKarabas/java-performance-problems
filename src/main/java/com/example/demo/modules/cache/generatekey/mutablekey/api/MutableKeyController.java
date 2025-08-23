package com.example.demo.modules.cache.generatekey.mutablekey.api;

import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.response.MutableKeyDTO;
import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.request.MutableKeySearchRequest;
import com.example.demo.modules.cache.generatekey.mutablekey.infrastructure.service.MutableKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cache/generatekey/mutablekey")
@RequiredArgsConstructor
@Slf4j
public class MutableKeyController {
    
    private final MutableKeyService mutableKeyService;
    
    @GetMapping("/info")
    public ResponseEntity<String> getCacheMutableKeyInfo() {
        String info = """
            # Cache Key Mutable Objects Problem
            
            ## Problem
            Using mutable objects (like List, Set, Map) as cache keys creates serious issues.
            When the mutable object is modified after being used as a key, the cache can no longer
            find the cached value, leading to cache misses and performance degradation.
            
            ## Demonstration
            1. PROBLEM: Using List<String> keyList as cache key - can be modified later!
            2. SOLUTION: String key = String.join(":", keyList) - immutable string
            
            ## Test Endpoints
            - POST /api/cache/generatekey/mutablekey/search/mutable-problem
            - POST /api/cache/generatekey/mutablekey/search/safe-string
            - DELETE /api/cache/generatekey/mutablekey/cache
            
            ## Expected Results
            - Problem: List keys can change after caching, causing cache misses
            - Solution: String.join(":", list) creates stable, immutable keys
            
            ## Common Mutable Objects to Avoid as Keys
            - ArrayList, LinkedList, Vector
            - HashSet, TreeSet, LinkedHashSet
            - HashMap, TreeMap, LinkedHashMap
            - Any custom mutable objects
            
            ## Safe Key Alternatives
            - String (immutable)
            - Primitive types (int, long, etc.)
            - Immutable objects (Integer, Long, etc.)
            - Records (if all fields are immutable)
            
            ## Testing
            1. Test data is auto-created via Flyway migration
            2. POST to endpoints with categories and tags arrays
            3. Observe cache behavior and key generation
            4. Check logs for cache hits/misses and warnings
            """;
        
        return ResponseEntity.ok(info);
    }
    
    @PostMapping("/search/mutable-problem")
    public ResponseEntity<String> demonstrateMutableKeyProblem(@RequestBody MutableKeySearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<MutableKeyDTO> results = mutableKeyService.searchWithMutableKeyProblem(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "MUTABLE CACHE KEY PROBLEM\n" +
            "Categories: %s\n" +
            "Tags: %s\n" +
            "Mutable cache key: %s\n" +
            "Results found: %d\n" +
            "Response time: %d ms\n" +
            "WARNING: This List key can be modified later!\n" +
            "Problem: keyList.add(\"new-item\") would change the cache key!\n" +
            "Impact: Cache misses, performance degradation, inconsistent behavior",
            request.getCategories(), request.getTags(),
            request.generateProblematicMutableCacheKey(), results.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/search/safe-string")
    public ResponseEntity<String> demonstrateSafeStringSolution(@RequestBody MutableKeySearchRequest request) {
        long startTime = System.currentTimeMillis();
        
        List<MutableKeyDTO> results = mutableKeyService.searchWithSafeStringKey(request);
        
        long endTime = System.currentTimeMillis();
        String result = String.format(
            "SAFE STRING KEY SOLUTION\n" +
            "Categories: %s\n" +
            "Tags: %s\n" +
            "Safe string key: '%s'\n" +
            "Results found: %d\n" +
            "Response time: %d ms\n" +
            "SUCCESS: Using String.join() creates immutable keys!\n" +
            "Benefit: Even if original List changes, cache key remains stable\n" +
            "Approach: String key = String.join(\":\", keyList);",
            request.getCategories(), request.getTags(),
            request.generateSafeStringCacheKey(), results.size(), (endTime - startTime)
        );
        
        return ResponseEntity.ok(result);
    }
    


    @DeleteMapping("/cache")
    public ResponseEntity<String> clearAllCaches() {
        mutableKeyService.clearAllCaches();
        return ResponseEntity.ok("All mutable key caches cleared. Next calls will hit database.");
    }
}
