package com.example.demo.modules.cache.generatekey.nullvalidation.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.response.NullValidationDTO;
import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.request.NullValidationSearchRequest;
import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.mapper.NullValidationMapper;
import com.example.demo.modules.cache.generatekey.nullvalidation.domain.entity.NullValidationCache;
import com.example.demo.modules.cache.generatekey.nullvalidation.infrastructure.repository.NullValidationRepository;
import com.example.demo.modules.cache.generatekey.nullvalidation.infrastructure.service.NullValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NullValidationServiceImpl implements NullValidationService {
    
    private final NullValidationRepository repository;
    private final NullValidationMapper mapper;
    
    @Override
    @Cacheable(value = "nullValidationProblematicCache", key = "#request.generateProblematicNullCacheKey()")
    public List<NullValidationDTO> searchWithNullValidationProblem(NullValidationSearchRequest request) {
        log.info("NULL VALIDATION CACHE KEY PROBLEM DEMONSTRATION");
        log.info("Request UserId: {}", request.getUserId());
        log.info("Request ProductId: {}", request.getProductId());
        log.info("Request CategoryId: {}", request.getCategoryId());
        log.info("Problematic Cache Key: '{}'", request.generateProblematicNullCacheKey());
        log.warn("WARNING: Using null values without validation in cache keys!");
        log.warn("This can result in cache keys containing 'null' strings!");
        log.warn("Example problem:");
        log.warn("   • userId = null; key = userId + \"_\" + productId;");
        log.warn("   • Result: 'null_123' - contains unwanted 'null' string!");
        log.warn("   • Can cause NullPointerException in some operations!");
        
        List<NullValidationCache> entities = repository.findByUserProductAndCategory(
            request.getUserId(), request.getProductId(), request.getCategoryId()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "nullValidationSafeCache", key = "#request.generateSafeNullValidatedCacheKey()")
    public List<NullValidationDTO> searchWithSafeNullValidation(NullValidationSearchRequest request) {
        log.info("NULL VALIDATION SAFE SOLUTION - TERNARY OPERATOR");
        log.info("Request UserId: {}", request.getUserId());
        log.info("Request ProductId: {}", request.getProductId());
        log.info("Request CategoryId: {}", request.getCategoryId());
        log.info("Safe Cache Key: '{}'", request.generateSafeNullValidatedCacheKey());
        log.info("SUCCESS: Using ternary operator for null validation!");
        log.info("Safe approach:");
        log.info("   • String safeUserId = (userId != null ? userId : \"guest\");");
        log.info("   • String key = safeUserId + \"_\" + productId + \"_\" + categoryId;");
        log.info("   • Result: Clean keys without 'null' strings!");
        
        List<NullValidationCache> entities = repository.findByUserProductAndCategory(
            request.getUserId(), request.getProductId(), request.getCategoryId()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "nullValidationOptionalCache", key = "#request.generateOptionalBasedCacheKey()")
    public List<NullValidationDTO> searchWithOptionalBasedValidation(NullValidationSearchRequest request) {
        log.info("NULL VALIDATION OPTIONAL SOLUTION");
        log.info("Request UserId: {}", request.getUserId());
        log.info("Request ProductId: {}", request.getProductId());
        log.info("Request CategoryId: {}", request.getCategoryId());
        log.info("Optional Cache Key: '{}'", request.generateOptionalBasedCacheKey());
        log.info("SUCCESS: Using Optional for null safety!");
        log.info("Optional approach:");
        log.info("   • String safeUserId = Optional.ofNullable(userId).orElse(\"guest\");");
        log.info("   • String key = safeUserId + \"_\" + productId + \"_\" + categoryId;");
        log.info("   • Result: Type-safe null handling with default values!");
        
        List<NullValidationCache> entities = repository.findByUserProductAndCategory(
            request.getUserId(), request.getProductId(), request.getCategoryId()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    

    @Override
    @CacheEvict(value = {
        "nullValidationProblematicCache", 
        "nullValidationSafeCache", 
        "nullValidationOptionalCache"
    }, allEntries = true)
    public void clearAllCaches() {
        log.info("CACHE CLEAR OPERATION");
        log.info("All null validation caches cleared successfully!");
        log.info("Next calls will hit database to demonstrate caching behavior");
    }
}
