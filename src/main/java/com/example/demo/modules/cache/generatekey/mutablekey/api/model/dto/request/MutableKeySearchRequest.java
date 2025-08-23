package com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutableKeySearchRequest {
    
    private List<String> categories;
    private List<String> tags;
    
    // PROBLEM: Using mutable List objects as cache keys
    // Lists can be modified after being used as keys, causing cache misses
    public List<String> generateProblematicMutableCacheKey() {
        List<String> keyList = new ArrayList<>();
        if (categories != null) {
            keyList.addAll(categories);
        }
        if (tags != null) {
            keyList.addAll(tags);
        }
        // WARNING: This List can be modified later, breaking cache behavior!
        return keyList;
    }
    
    // SOLUTION: Convert mutable List to immutable String
    public String generateSafeStringCacheKey() {
        List<String> keyComponents = new ArrayList<>();
        if (categories != null) {
            keyComponents.addAll(categories);
        }
        if (tags != null) {
            keyComponents.addAll(tags);
        }
        // Safe: Convert to immutable string with delimiter
        return String.join(":", keyComponents);
    }
    


}
