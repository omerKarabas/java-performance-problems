package com.example.demo.modules.cache.generatekey.mutablekey.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.response.MutableKeyDTO;
import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.request.MutableKeySearchRequest;
import com.example.demo.modules.cache.generatekey.mutablekey.api.model.mapper.MutableKeyMapper;
import com.example.demo.modules.cache.generatekey.mutablekey.domain.entity.MutableKeyCache;
import com.example.demo.modules.cache.generatekey.mutablekey.infrastructure.repository.MutableKeyRepository;
import com.example.demo.modules.cache.generatekey.mutablekey.infrastructure.service.MutableKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MutableKeyServiceImpl implements MutableKeyService {
    
    private final MutableKeyRepository repository;
    private final MutableKeyMapper mapper;
    
    @Override
    @Cacheable(value = "mutableKeyProblematicCache", key = "#request.generateProblematicMutableCacheKey()")
    public List<MutableKeyDTO> searchWithMutableKeyProblem(MutableKeySearchRequest request) {
        log.info("MUTABLE CACHE KEY PROBLEM DEMONSTRATION");
        log.info("Request Categories: {}", request.getCategories());
        log.info("Request Tags: {}", request.getTags());
        log.info("Problematic Cache Key (List): {}", request.generateProblematicMutableCacheKey());
        log.warn("WARNING: Using mutable List objects as cache keys!");
        log.warn("This List can be modified after caching, causing cache misses!");
        log.warn("Example problem:");
        log.warn("   • keyList.add(\"item1\"); cache.put(keyList, value);");
        log.warn("   • keyList.add(\"item2\"); // Now cache key changed!");
        log.warn("   • cache.get(keyList); // CACHE MISS!");
        
        List<MutableKeyCache> entities = repository.findByCategoriesAndTags(
            request.getCategories(), request.getTags()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "mutableKeySafeStringCache", key = "#request.generateSafeStringCacheKey()")
    public List<MutableKeyDTO> searchWithSafeStringKey(MutableKeySearchRequest request) {
        log.info("MUTABLE KEY SAFE STRING SOLUTION");
        log.info("Request Categories: {}", request.getCategories());
        log.info("Request Tags: {}", request.getTags());
        log.info("Safe Cache Key (String): '{}'", request.generateSafeStringCacheKey());
        log.info("SUCCESS: Converting mutable List to immutable String!");
        log.info("Safe approach:");
        log.info("   • String key = String.join(\":\", keyList);");
        log.info("   • cache.put(key, value); // Immutable key!");
        log.info("   • Even if original List changes, cache key remains stable");
        
        List<MutableKeyCache> entities = repository.findByCategoriesAndTags(
            request.getCategories(), request.getTags()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    


    @Override
    @CacheEvict(value = {"mutableKeyProblematicCache", "mutableKeySafeStringCache"}, allEntries = true)
    public void clearAllCaches() {
        log.info("CACHE CLEAR OPERATION");
        log.info("All mutable key caches cleared successfully!");
        log.info("Next calls will hit database to demonstrate caching behavior");
    }
}
