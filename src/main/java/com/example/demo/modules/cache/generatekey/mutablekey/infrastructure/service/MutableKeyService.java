package com.example.demo.modules.cache.generatekey.mutablekey.infrastructure.service;

import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.response.MutableKeyDTO;
import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.request.MutableKeySearchRequest;

import java.util.List;

public interface MutableKeyService {
    
    /**
     * Demonstrates the mutable object cache key problem
     * Uses problematic cache key generation with mutable List objects
     */
    List<MutableKeyDTO> searchWithMutableKeyProblem(MutableKeySearchRequest request);
    
    /**
     * Demonstrates the solution using immutable String cache keys
     * Uses String.join() to create safe cache keys
     */
    List<MutableKeyDTO> searchWithSafeStringKey(MutableKeySearchRequest request);
    


    /**
     * Clears all caches for demonstration
     */
    void clearAllCaches();
}
