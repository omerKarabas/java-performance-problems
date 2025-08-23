package com.example.demo.modules.cache.generatekey.casesensitivity.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.request.CaseSensitivitySearchRequest;
import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.response.CaseSensitivityUserDTO;
import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.mapper.CaseSensitivityUserMapper;
import com.example.demo.modules.cache.generatekey.casesensitivity.domain.entity.CaseSensitivityUser;
import com.example.demo.modules.cache.generatekey.casesensitivity.infrastructure.repository.CaseSensitivityUserRepository;
import com.example.demo.modules.cache.generatekey.casesensitivity.infrastructure.service.CaseSensitivityUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseSensitivityUserServiceImpl implements CaseSensitivityUserService {
    
    private final CaseSensitivityUserRepository repository;
    private final CaseSensitivityUserMapper mapper;
    
    // Manual cache for demonstration purposes
    private final Map<String, List<CaseSensitivityUserDTO>> manualCache = new ConcurrentHashMap<>();
    private final Map<String, Integer> cacheHitStats = new ConcurrentHashMap<>();
    private final Map<String, Integer> cacheMissStats = new ConcurrentHashMap<>();
    
    @Override
    @Cacheable(value = "caseSensitiveProblem", key = "#request.generateProblematicCacheKey()")
    public List<CaseSensitivityUserDTO> searchWithCaseSensitiveProblem(CaseSensitivitySearchRequest request) {
        String cacheKey = request.generateProblematicCacheKey();
        log.warn("PROBLEM: Case-sensitive cache key used: {}", cacheKey);
        
        // Check manual cache to demonstrate the problem
        if (manualCache.containsKey(cacheKey)) {
            log.info("Cache HIT for key: {}", cacheKey);
            incrementCacheHit("caseSensitiveProblem");
            return manualCache.get(cacheKey);
        }
        
        log.warn("Cache MISS for key: {} - This happens when case differs!", cacheKey);
        incrementCacheMiss("caseSensitiveProblem");
        
        // Simulate database query
        List<CaseSensitivityUser> users = performSearch(request);
        List<CaseSensitivityUserDTO> result = mapper.toDTOList(users);
        
        // Store in manual cache with problematic key
        manualCache.put(cacheKey, result);
        
        return result;
    }
    
    @Override
    @Cacheable(value = "normalizedKeys", key = "#request.generateNormalizedCacheKey()")
    public List<CaseSensitivityUserDTO> searchWithNormalizedKeys(CaseSensitivitySearchRequest request) {
        String cacheKey = request.generateNormalizedCacheKey();
        log.info("SOLUTION: Normalized cache key used: {}", cacheKey);
        
        // Check manual cache
        if (manualCache.containsKey(cacheKey)) {
            log.info("Cache HIT for normalized key: {}", cacheKey);
            incrementCacheHit("normalizedKeys");
            return manualCache.get(cacheKey);
        }
        
        log.info("Cache MISS for normalized key: {} - First time access", cacheKey);
        incrementCacheMiss("normalizedKeys");
        
        // Perform search
        List<CaseSensitivityUser> users = performSearch(request);
        List<CaseSensitivityUserDTO> result = mapper.toDTOList(users);
        
        // Store with normalized key
        manualCache.put(cacheKey, result);
        
        return result;
    }
    
    @Override
    @Cacheable(value = "robustKeys", key = "#request.generateRobustCacheKey()")
    public List<CaseSensitivityUserDTO> searchWithRobustKeys(CaseSensitivitySearchRequest request) {
        String cacheKey = request.generateRobustCacheKey();
        log.info("ADVANCED: Robust cache key used: {}", cacheKey);
        
        if (manualCache.containsKey(cacheKey)) {
            log.info("Cache HIT for robust key: {}", cacheKey);
            incrementCacheHit("robustKeys");
            return manualCache.get(cacheKey);
        }
        
        log.info("Cache MISS for robust key: {} - First time access", cacheKey);
        incrementCacheMiss("robustKeys");
        
        List<CaseSensitivityUser> users = performSearch(request);
        List<CaseSensitivityUserDTO> result = mapper.toDTOList(users);
        
        manualCache.put(cacheKey, result);
        
        return result;
    }
    
    @Override
    @Cacheable(value = "hashBasedKeys", key = "#request.generateHashBasedCacheKey()")
    public List<CaseSensitivityUserDTO> searchWithHashBasedKeys(CaseSensitivitySearchRequest request) {
        String cacheKey = request.generateHashBasedCacheKey();
        log.info("HASH-BASED: Hash cache key used: {}", cacheKey);
        
        if (manualCache.containsKey(cacheKey)) {
            log.info("Cache HIT for hash key: {}", cacheKey);
            incrementCacheHit("hashBasedKeys");
            return manualCache.get(cacheKey);
        }
        
        log.info("Cache MISS for hash key: {} - First time access", cacheKey);
        incrementCacheMiss("hashBasedKeys");
        
        List<CaseSensitivityUser> users = performSearch(request);
        List<CaseSensitivityUserDTO> result = mapper.toDTOList(users);
        
        manualCache.put(cacheKey, result);
        
        return result;
    }
    
    @Override
    public Map<String, Object> getCaseSensitivityInfo() {
        Map<String, Object> info = new HashMap<>();
        
        info.put("problemDescription", "Case sensitivity in cache keys causes cache misses for logically identical data");
        info.put("example", Map.of(
            "problematic", "John_Electronics vs john_electronics",
            "solution", "john_electronics (normalized)"
        ));
        
        info.put("solutions", Arrays.asList(
            "Normalize to lowercase: userName.toLowerCase()",
            "Handle null values and trim whitespace",
            "Use hash-based keys for complex scenarios",
            "Implement consistent key generation strategy"
        ));
        
        info.put("bestPractices", Arrays.asList(
            "Always normalize case in cache keys",
            "Handle null and empty values",
            "Trim whitespace before key generation",
            "Document key generation strategy",
            "Test with different case variations",
            "Monitor cache hit ratios"
        ));
        
        return info;
    }
    
    @Override
    @CacheEvict(value = {"caseSensitiveProblem", "normalizedKeys", "robustKeys", "hashBasedKeys"}, allEntries = true)
    public void clearAllCaches() {
        manualCache.clear();
        cacheHitStats.clear();
        cacheMissStats.clear();
        log.info("All caches cleared for case sensitivity testing");
    }
    
    @Override
    public Map<String, Object> getCacheStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("manualCacheSize", manualCache.size());
        stats.put("cacheHits", new HashMap<>(cacheHitStats));
        stats.put("cacheMisses", new HashMap<>(cacheMissStats));
        
        // Calculate hit ratios
        Map<String, Double> hitRatios = new HashMap<>();
        for (String key : cacheHitStats.keySet()) {
            int hits = cacheHitStats.getOrDefault(key, 0);
            int misses = cacheMissStats.getOrDefault(key, 0);
            double ratio = (hits + misses > 0) ? (double) hits / (hits + misses) : 0.0;
            hitRatios.put(key, ratio);
        }
        stats.put("hitRatios", hitRatios);
        
        return stats;
    }
    
    private List<CaseSensitivityUser> performSearch(CaseSensitivitySearchRequest request) {
        // Simulate some processing delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Try to find existing users, otherwise create sample data
        List<CaseSensitivityUser> users = repository.findByUserNameAndCategoryAndRegionIgnoreCase(
            request.getUserName(), request.getCategory(), request.getRegion());
        
        if (users.isEmpty()) {
            // Create and save sample user
            CaseSensitivityUser sampleUser = mapper.createSampleEntity(request);
            sampleUser = repository.save(sampleUser);
            users = List.of(sampleUser);
        }
        
        return users;
    }
    
    private void incrementCacheHit(String cacheType) {
        cacheHitStats.merge(cacheType, 1, Integer::sum);
    }
    
    private void incrementCacheMiss(String cacheType) {
        cacheMissStats.merge(cacheType, 1, Integer::sum);
    }
}
