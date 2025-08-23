package com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.request;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {
    
    private ProductCategory category;
    
    // Generate consistent cache key based on actual content
    public String generateCacheKey() {
        return "category:" + (category != null ? category : "null");
    }
}
