package com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseSensitivitySearchRequest {
    
    private String userName;
    private String category;
    private String region;
    
    // PROBLEM: Case sensitivity in cache keys causes different keys for same logical data
    // "John" + "Electronics" != "john" + "electronics"
    public String generateProblematicCacheKey() {
        // WRONG: Case differences create different cache keys
        return userName + "_" + category + "_" + region;
    }
    
    // SOLUTION: Normalize to lowercase before concatenation
    public String generateNormalizedCacheKey() {
        // CORRECT: Normalize all values to lowercase
        String normalizedUserName = userName != null ? userName.toLowerCase() : "";
        String normalizedCategory = category != null ? category.toLowerCase() : "";
        String normalizedRegion = region != null ? region.toLowerCase() : "";
        
        return normalizedUserName + "_" + normalizedCategory + "_" + normalizedRegion;
    }
    
    // ADVANCED SOLUTION: Handle null values and trim whitespace
    public String generateRobustCacheKey() {
        // ADVANCED: Robust key with null checks and whitespace trimming
        String normalizedUserName = normalizeField(userName);
        String normalizedCategory = normalizeField(category);
        String normalizedRegion = normalizeField(region);
        
        return normalizedUserName + "_" + normalizedCategory + "_" + normalizedRegion;
    }
    
    private String normalizeField(String field) {
        if (field == null) {
            return "";
        }
        return field.trim().toLowerCase();
    }
    
    // HASH-BASED SOLUTION: More consistent for complex scenarios
    public String generateHashBasedCacheKey() {
        String normalizedData = generateRobustCacheKey();
        return String.valueOf(normalizedData.hashCode());
    }
}
