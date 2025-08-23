package com.example.demo.modules.cache.generatekey.consistentordering.infrastructure.service;

import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.response.SearchParameterDTO;
import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.request.ConsistentOrderingSearchRequest;

import java.util.List;

public interface ConsistentOrderingService {
    
    /**
     * Demonstrates the consistent ordering problem
     * Uses problematic cache key generation (Set<String> without ordering)
     */
    List<SearchParameterDTO> getSearchResultsWithOrderingProblem(ConsistentOrderingSearchRequest request);
    
    /**
     * Demonstrates the solution using sorted cache key generation
     * Uses sorted List<String> for cache key consistency
     */
    List<SearchParameterDTO> getSearchResultsWithConsistentOrdering(ConsistentOrderingSearchRequest request);
    
    /**
     * Demonstrates alternative solution using TreeSet for automatic ordering
     * Uses TreeSet<String> which maintains natural ordering
     */
    List<SearchParameterDTO> getSearchResultsWithTreeSetOrdering(ConsistentOrderingSearchRequest request);
    
    /**
     * Clears all caches for demonstration
     */
    void clearAllCaches();
}
