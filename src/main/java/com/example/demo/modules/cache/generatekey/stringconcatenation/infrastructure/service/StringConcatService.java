package com.example.demo.modules.cache.generatekey.stringconcatenation.infrastructure.service;

import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.response.StringConcatDTO;
import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.request.StringConcatSearchRequest;

import java.util.List;

public interface StringConcatService {
    
    /**
     * Demonstrates the string concatenation collision problem
     * Uses problematic cache key generation (userId + productId)
     */
    List<StringConcatDTO> getUserProductsWithCollisionProblem(StringConcatSearchRequest request);
    
    /**
     * Demonstrates the solution using safe cache key generation
     * Uses delimiter-based cache key (userId:productId)
     */
    List<StringConcatDTO> getUserProductsWithSafeCacheKey(StringConcatSearchRequest request);
    
    /**
     * Demonstrates alternative solution using hash-based cache key
     * Uses prefix-based cache key (userproduct:userId:productId)
     */
    List<StringConcatDTO> getUserProductsWithHashBasedCacheKey(StringConcatSearchRequest request);
    
    /**
     * Clears all caches for demonstration
     */
    void clearAllCaches();
}
