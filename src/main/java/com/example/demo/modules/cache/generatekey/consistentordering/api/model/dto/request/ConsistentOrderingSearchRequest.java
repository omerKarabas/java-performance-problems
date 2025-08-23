package com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsistentOrderingSearchRequest {
    
    private String category;
    private String brand;
    private String region;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minRating;
    
    public Set<String> getSearchParams() {
        Set<String> params = new HashSet<>();
        if (category != null && !category.isEmpty()) params.add(category);
        if (brand != null && !brand.isEmpty()) params.add(brand);
        if (region != null && !region.isEmpty()) params.add(region);
        return params;
    }
    
    // PROBLEM: HashSet ordering is not guaranteed, causing cache key inconsistency
    // Same parameters in different order can create different cache keys
    public String generateProblematicCacheKey() {
        Set<String> params = getSearchParams();
        // WARNING: HashSet iteration order is not guaranteed!
        // This can cause ["category", "brand", "region"] to become "categorybrandregion"
        // or ["brand", "category", "region"] to become "brandcategoryregion"
        return String.join("", params) + "_" + minPrice + "_" + maxPrice + "_" + minRating;
    }
    
    // SOLUTION: Sort parameters before joining to ensure consistent ordering
    public String generateSortedCacheKey() {
        Set<String> params = getSearchParams();
        List<String> sortedParams = new ArrayList<>(params);
        Collections.sort(sortedParams); // Ensures consistent ordering
        return String.join("_", sortedParams) + "_" + minPrice + "_" + maxPrice + "_" + minRating;
    }
    
    // ALTERNATIVE SOLUTION: Use TreeSet which maintains natural ordering automatically
    public String generateTreeSetCacheKey() {
        Set<String> params = getSearchParams();
        TreeSet<String> sortedParams = new TreeSet<>(params); // Automatically sorted
        return String.join("_", sortedParams) + "_" + minPrice + "_" + maxPrice + "_" + minRating;
    }
}
