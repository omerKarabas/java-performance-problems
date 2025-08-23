package com.example.demo.modules.cache.generatekey.casesensitivity.api;

import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.request.CaseSensitivitySearchRequest;
import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.response.CaseSensitivityUserDTO;
import com.example.demo.modules.cache.generatekey.casesensitivity.infrastructure.service.CaseSensitivityUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/cache/generatekey/casesensitivity")
@RequiredArgsConstructor
public class CaseSensitivityController {
    
    private final CaseSensitivityUserService caseSensitivityUserService;
    
    /**
     * Get detailed information about case sensitivity problems
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getCaseSensitivityInfo() {
        log.info("Getting case sensitivity information");
        Map<String, Object> info = caseSensitivityUserService.getCaseSensitivityInfo();
        return ResponseEntity.ok(info);
    }
    
    /**
     * PROBLEM: Demonstrates case sensitivity issue
     * Example: {"userName": "John", "category": "Electronics", "region": "US"}
     * Then: {"userName": "john", "category": "electronics", "region": "us"}
     * You'll see different cache keys are created!
     */
    @PostMapping("/search/case-sensitive-problem")
    public ResponseEntity<Map<String, Object>> searchWithCaseSensitiveProblem(
            @RequestBody CaseSensitivitySearchRequest request) {
        
        log.warn("CASE SENSITIVITY PROBLEM - Request: {}", request);
        
        List<CaseSensitivityUserDTO> results = caseSensitivityUserService.searchWithCaseSensitiveProblem(request);
        
        Map<String, Object> response = Map.of(
            "method", "PROBLEM: Case-sensitive cache key",
            "cacheKey", request.generateProblematicCacheKey(),
            "issue", "Case differences create different cache keys",
            "example", "John_Electronics_US ≠ john_electronics_us",
            "results", results,
            "resultCount", results.size()
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * SOLUTION: Normalized cache keys
     * Same logical data always produces the same cache key
     */
    @PostMapping("/search/case-insensitive-solution")
    public ResponseEntity<Map<String, Object>> searchWithNormalizedKeys(
            @RequestBody CaseSensitivitySearchRequest request) {
        
        log.info("CASE INSENSITIVE SOLUTION - Request: {}", request);
        
        List<CaseSensitivityUserDTO> results = caseSensitivityUserService.searchWithNormalizedKeys(request);
        
        Map<String, Object> response = Map.of(
            "method", "SOLUTION: Normalized cache key",
            "cacheKey", request.generateNormalizedCacheKey(),
            "solution", "All values are normalized to lowercase",
            "example", "john_electronics_us (always consistent)",
            "results", results,
            "resultCount", results.size()
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Clear all caches
     */
    @DeleteMapping("/cache")
    public ResponseEntity<Map<String, String>> clearAllCaches() {
        log.info("Clearing all case sensitivity caches");
        caseSensitivityUserService.clearAllCaches();
        
        Map<String, String> response = Map.of(
            "message", "All case sensitivity caches cleared successfully",
            "timestamp", String.valueOf(System.currentTimeMillis())
        );
        
        return ResponseEntity.ok(response);
    }
}
