package com.example.demo.modules.cache.generatekey.consistentordering.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.response.SearchParameterDTO;
import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.request.ConsistentOrderingSearchRequest;
import com.example.demo.modules.cache.generatekey.consistentordering.api.model.mapper.SearchParameterMapper;
import com.example.demo.modules.cache.generatekey.consistentordering.domain.entity.SearchParameter;
import com.example.demo.modules.cache.generatekey.consistentordering.infrastructure.repository.SearchParameterRepository;
import com.example.demo.modules.cache.generatekey.consistentordering.infrastructure.service.ConsistentOrderingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsistentOrderingServiceImpl implements ConsistentOrderingService {
    
    private final SearchParameterRepository repository;
    private final SearchParameterMapper mapper;
    
    @Override
    @Cacheable(value = "consistentOrderingProblemCache", key = "#request.generateProblematicCacheKey()")
    public List<SearchParameterDTO> getSearchResultsWithOrderingProblem(ConsistentOrderingSearchRequest request) {
        log.info("CONSISTENT ORDERING PROBLEM");
        log.info("Request Details:");
        log.info("   • Parameters: {}", request.getSearchParams());
        log.info("   • Cache Key: '{}'", request.generateProblematicCacheKey());
        log.info("WARNING: This cache key can be inconsistent due to Set ordering!");
        log.info("Example:");
        log.info("   • Set[category, brand, region] might become: categorybrandregion");
        log.info("   • Set[brand, category, region] might become: brandcategoryregion");
        log.info("   • Same parameters, different cache keys!");
        
        List<SearchParameter> entities = repository.findBySearchCriteria(
            request.getCategory(), request.getBrand(), request.getRegion(),
            request.getMinPrice(), request.getMaxPrice(), request.getMinRating()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "consistentOrderingSafeCache", key = "#request.generateSortedCacheKey()")
    public List<SearchParameterDTO> getSearchResultsWithConsistentOrdering(ConsistentOrderingSearchRequest request) {
        log.info("CONSISTENT ORDERING SAFE SOLUTION");
        log.info("Request Details:");
        log.info("   • Parameters: {}", request.getSearchParams());
        log.info("   • Cache Key: '{}'", request.generateSortedCacheKey());
        log.info("SUCCESS: Using sorted parameters ensures consistent cache keys!");
        log.info("Example:");
        log.info("   • Sorted: [brand, category, region] always becomes: brand_category_region");
        log.info("   • Same input always generates same cache key!");
        
        List<SearchParameter> entities = repository.findBySearchCriteria(
            request.getCategory(), request.getBrand(), request.getRegion(),
            request.getMinPrice(), request.getMaxPrice(), request.getMinRating()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @Cacheable(value = "consistentOrderingTreeSetCache", key = "#request.generateTreeSetCacheKey()")
    public List<SearchParameterDTO> getSearchResultsWithTreeSetOrdering(ConsistentOrderingSearchRequest request) {
        log.info("CONSISTENT ORDERING TREESET SOLUTION");
        log.info("Request Details:");
        log.info("   • Parameters: {}", request.getSearchParams());
        log.info("   • Cache Key: '{}'", request.generateTreeSetCacheKey());
        log.info("SUCCESS: Using TreeSet automatically maintains order!");
        log.info("Example:");
        log.info("   • TreeSet automatically sorts: [brand, category, region]");
        log.info("   • Natural ordering ensures consistency!");
        
        List<SearchParameter> entities = repository.findBySearchCriteria(
            request.getCategory(), request.getBrand(), request.getRegion(),
            request.getMinPrice(), request.getMaxPrice(), request.getMinRating()
        );
        log.info("Database query completed - Found {} records", entities.size());
        
        return mapper.toDTOList(entities);
    }
    
    @Override
    @CacheEvict(value = {"consistentOrderingProblemCache", "consistentOrderingSafeCache", "consistentOrderingTreeSetCache"}, allEntries = true)
    public void clearAllCaches() {
        log.info("CACHE CLEAR OPERATION");
        log.info("All consistent ordering caches cleared successfully!");
    }
}
