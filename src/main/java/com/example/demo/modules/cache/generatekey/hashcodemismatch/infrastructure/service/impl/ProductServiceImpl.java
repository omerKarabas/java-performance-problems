package com.example.demo.modules.cache.generatekey.hashcodemismatch.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.response.ProductDTO;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.request.ProductSearchRequest;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.mapper.ProductMapper;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.Product;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.infrastructure.repository.ProductRepository;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    @Override
    @Cacheable(value = "products", key = "#request.hashCode()")  // PROBLEM: Request object hashCode as cache key
    public List<ProductDTO> getProductsByCategoryWithProblem(ProductSearchRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("=== CACHE KEY HASHCODE PROBLEM DEMONSTRATION ===");
        
        // Log request details in structured format
        log.info("Request: category={}, hashCode={}, toString={}", 
                request.getCategory(), request.hashCode(), request.toString());
        
        // Demonstrate the problem: same data, different hashCode
        ProductSearchRequest sameDataRequest = new ProductSearchRequest(request.getCategory());
        log.info("PROBLEM: Same data ({}) produces different hashCode: original={}, new={}", 
                request.getCategory(), request.hashCode(), sameDataRequest.hashCode());
        
        // Execute database query
        List<Product> products = productRepository.findByCategory(request.getCategory());
        long endTime = System.currentTimeMillis();
        
        log.info("Database query executed - Retrieved {} products for category: {} in {}ms", 
                products.size(), request.getCategory(), (endTime - startTime));
        
        return productMapper.toDTOList(products);
    }
    
    @Override
    @Cacheable(value = "products", key = "#request.generateCacheKey()")  // SOLUTION: Custom cache key generation
    public List<ProductDTO> getProductsByCategoryWithSolution(ProductSearchRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("=== CACHE KEY SOLUTION DEMONSTRATION ===");
        
        // Generate and log cache key
        String cacheKey = request.generateCacheKey();
        log.info("Generated cache key: '{}' for category: {}", cacheKey, request.getCategory());
        
        // Demonstrate the solution: same data, same cache key
        ProductSearchRequest sameDataRequest = new ProductSearchRequest(request.getCategory());
        String sameDataCacheKey = sameDataRequest.generateCacheKey();
        log.info("SOLUTION: Same data ({}) produces same cache key: '{}' (match: {})", 
                request.getCategory(), sameDataCacheKey, cacheKey.equals(sameDataCacheKey));
        
        // Execute database query
        List<Product> products = productRepository.findByCategory(request.getCategory());
        long endTime = System.currentTimeMillis();
        
        log.info("Database query executed - Retrieved {} products for category: {} in {}ms", 
                products.size(), request.getCategory(), (endTime - startTime));
        
        return productMapper.toDTOList(products);
    }
        
    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void clearAllCaches() {
        log.info("=== CACHE CLEAR OPERATION ===");
        log.info("Clearing all product caches - All entries will be evicted");
    }

}
