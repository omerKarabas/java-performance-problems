package com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NullValidationSearchRequest {
    
    private String userId;     // Can be null to demonstrate the problem
    private String productId;  // Should not be null
    private String categoryId; // Can be null
    
    // PROBLEM: Not validating null values before cache key generation
    // This can result in cache keys containing "null" strings
    public String generateProblematicNullCacheKey() {
        // WARNING: No null validation - can create "null_123_null" keys!
        return userId + "_" + productId + "_" + categoryId;
    }
    
    // SOLUTION: Proper null validation with default values
    public String generateSafeNullValidatedCacheKey() {
        // Safe: Using ternary operator for null validation
        String safeUserId = (userId != null ? userId : "guest");
        String safeProductId = (productId != null ? productId : "unknown");
        String safeCategoryId = (categoryId != null ? categoryId : "general");
        
        return safeUserId + "_" + safeProductId + "_" + safeCategoryId;
    }
    
    // ALTERNATIVE SOLUTION: Using Optional for null safety
    public String generateOptionalBasedCacheKey() {
        String safeUserId = Optional.ofNullable(userId).orElse("guest");
        String safeProductId = Optional.ofNullable(productId).orElse("unknown");
        String safeCategoryId = Optional.ofNullable(categoryId).orElse("general");
        
        return safeUserId + "_" + safeProductId + "_" + safeCategoryId;
    }
    
    // UTILITY METHOD: Safe string with custom default
    private String safeString(String value, String defaultValue) {
        return value != null && !value.trim().isEmpty() ? value : defaultValue;
    }
    
    // ADVANCED SOLUTION: Using utility method for consistent null handling
    public String generateUtilityBasedCacheKey() {
        String safeUserId = safeString(userId, "guest");
        String safeProductId = safeString(productId, "unknown");
        String safeCategoryId = safeString(categoryId, "general");
        
        return safeUserId + "_" + safeProductId + "_" + safeCategoryId;
    }
}
