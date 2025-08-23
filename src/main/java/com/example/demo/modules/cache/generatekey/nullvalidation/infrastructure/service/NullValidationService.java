package com.example.demo.modules.cache.generatekey.nullvalidation.infrastructure.service;

import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.response.NullValidationDTO;
import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.request.NullValidationSearchRequest;

import java.util.List;

public interface NullValidationService {
    
    /**
     * Demonstrates the null value cache key problem
     * Uses problematic cache key generation without null validation
     */
    List<NullValidationDTO> searchWithNullValidationProblem(NullValidationSearchRequest request);
    
    /**
     * Demonstrates the solution using proper null validation
     * Uses ternary operator for null value validation
     */
    List<NullValidationDTO> searchWithSafeNullValidation(NullValidationSearchRequest request);
    
    /**
     * Demonstrates alternative solution using Optional for null safety
     * Uses Optional.ofNullable().orElse() pattern
     */
    List<NullValidationDTO> searchWithOptionalBasedValidation(NullValidationSearchRequest request);
    

    /**
     * Clears all caches for demonstration
     */
    void clearAllCaches();
}
