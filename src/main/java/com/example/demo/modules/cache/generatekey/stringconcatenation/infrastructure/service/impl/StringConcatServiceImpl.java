package com.example.demo.modules.cache.generatekey.stringconcatenation.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.response.StringConcatDTO;
import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.request.StringConcatSearchRequest;
import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.mapper.StringConcatMapper;
import com.example.demo.modules.cache.generatekey.stringconcatenation.domain.entity.StringConcatUser;
import com.example.demo.modules.cache.generatekey.stringconcatenation.infrastructure.repository.StringConcatRepository;
import com.example.demo.modules.cache.generatekey.stringconcatenation.infrastructure.service.StringConcatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StringConcatServiceImpl implements StringConcatService {
    
    private final StringConcatRepository repository;
    private final StringConcatMapper mapper;
    
    @Override
    @Cacheable(value = "stringConcatCollisionCache", key = "#request.generateProblematicCacheKey()")
    public List<StringConcatDTO> getUserProductsWithCollisionProblem(StringConcatSearchRequest request) {
        log.info("STRING CONCATENATION COLLISION PROBLEM");
        log.info("Request Details:");
        log.info("   • User ID: {}", request.getUserId());
        log.info("   • Product ID: {}", request.getProductId());
        log.info("   • Cache Key: '{}'", request.generateProblematicCacheKey());
        log.info("WARNING: This cache key can cause collisions!");
        log.info("Example:");
        log.info("   • user123+product45 = user123product45");
        log.info("   • user12+3product45 = user123product45 (SAME KEY!)");
        
        List<StringConcatUser> entities = repository.findByUserIdAndProductId(request.getUserId(), request.getProductId());
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "stringConcatSafeCache", key = "#request.generateSafeCacheKey()")
    public List<StringConcatDTO> getUserProductsWithSafeCacheKey(StringConcatSearchRequest request) {
        log.info("STRING CONCATENATION SAFE SOLUTION");
        log.info("Request Details:");
        log.info("   • User ID: {}", request.getUserId());
        log.info("   • Product ID: {}", request.getProductId());
        log.info("   • Cache Key: '{}'", request.generateSafeCacheKey());
        log.info("SUCCESS: Using delimiter ':' prevents collisions!");
        log.info("Example:");
        log.info("   • user123:product45 ≠ user12:3product45");
        
        List<StringConcatUser> entities = repository.findByUserIdAndProductId(request.getUserId(), request.getProductId());
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "stringConcatHashCache", key = "#request.generateHashBasedCacheKey()")
    public List<StringConcatDTO> getUserProductsWithHashBasedCacheKey(StringConcatSearchRequest request) {
        log.info("STRING CONCATENATION HASH-BASED SOLUTION");
        log.info("Request Details:");
        log.info("   • User ID: {}", request.getUserId());
        log.info("   • Product ID: {}", request.getProductId());
        log.info("   • Cache Key: '{}'", request.generateHashBasedCacheKey());
        log.info("SUCCESS: Using prefix and delimiters ensures uniqueness!");
        log.info("Example:");
        log.info("   • userproduct:user123:product45");
        
        List<StringConcatUser> entities = repository.findByUserIdAndProductId(request.getUserId(), request.getProductId());
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @CacheEvict(value = {"stringConcatCollisionCache", "stringConcatSafeCache", "stringConcatHashCache"}, allEntries = true)
    public void clearAllCaches() {
        log.info("CACHE CLEAR OPERATION");
        log.info("All string concatenation caches cleared successfully!");
    }
}
