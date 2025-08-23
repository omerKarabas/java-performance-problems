package com.example.demo.modules.cache.generatekey.casesensitivity.infrastructure.service;

import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.request.CaseSensitivitySearchRequest;
import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.response.CaseSensitivityUserDTO;

import java.util.List;
import java.util.Map;

public interface CaseSensitivityUserService {
    
    /**
     * Demonstrates case sensitivity problem with cache keys
     * Same logical data with different case creates different cache entries
     */
    List<CaseSensitivityUserDTO> searchWithCaseSensitiveProblem(CaseSensitivitySearchRequest request);
    
    /**
     * Solution: Normalized lowercase cache keys
     * Consistent cache behavior regardless of input case
     */
    List<CaseSensitivityUserDTO> searchWithNormalizedKeys(CaseSensitivitySearchRequest request);
    
    /**
     * Advanced solution: Robust key generation with null handling
     * Handles edge cases like null values and whitespace
     */
    List<CaseSensitivityUserDTO> searchWithRobustKeys(CaseSensitivitySearchRequest request);
    
    /**
     * Hash-based solution for complex scenarios
     * More consistent for complex key generation
     */
    List<CaseSensitivityUserDTO> searchWithHashBasedKeys(CaseSensitivitySearchRequest request);
    
    /**
     * Get detailed information about case sensitivity problems
     */
    Map<String, Object> getCaseSensitivityInfo();
    
    /**
     * Clear all caches for testing purposes
     */
    void clearAllCaches();
    
    /**
     * Get cache statistics for analysis
     */
    Map<String, Object> getCacheStatistics();
}
