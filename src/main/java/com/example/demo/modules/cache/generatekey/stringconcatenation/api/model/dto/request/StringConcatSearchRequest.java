package com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringConcatSearchRequest {
    
    private String userId;
    private String productId;
    
    // PROBLEM: String concatenation collision risk
    // "user123" + "product45" = "user123product45"
    // "user12" + "3product45" = "user123product45" - SAME KEY!
    public String generateProblematicCacheKey() {
        return userId + productId;
    }
    
    // SOLUTION: Prevent collision by using delimiter
    public String generateSafeCacheKey() {
        return userId + ":" + productId;
    }
    
    // ALTERNATIVE SOLUTION: Use hash prefix
    public String generateHashBasedCacheKey() {
        return "userproduct:" + userId + ":" + productId;
    }
}
