package com.example.demo.modules.cache.generatekey.hashcodemismatch.infrastructure.service;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.response.ProductDTO;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.request.ProductSearchRequest;

import java.util.List;

public interface ProductService {
    
    /**
     * Demonstrates the cache key hashCode problem
     * Uses request object as cache key which causes inconsistent hashCode
     */
    List<ProductDTO> getProductsByCategoryWithProblem(ProductSearchRequest request);
    
    /**
     * Demonstrates the solution using proper cache key generation
     * Uses request.generateCacheKey() for consistent caching
     */
    List<ProductDTO> getProductsByCategoryWithSolution(ProductSearchRequest request);
    
    /**
     * Clears all caches for demonstration
     */
    void clearAllCaches();
}
