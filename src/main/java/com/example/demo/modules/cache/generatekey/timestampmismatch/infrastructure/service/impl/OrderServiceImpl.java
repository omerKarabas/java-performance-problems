package com.example.demo.modules.cache.generatekey.timestampmismatch.infrastructure.service.impl;

import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.response.OrderDTO;
import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.request.OrderSearchRequest;
import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.mapper.OrderMapper;
import com.example.demo.modules.cache.generatekey.timestampmismatch.domain.entity.Order;
import com.example.demo.modules.cache.generatekey.timestampmismatch.infrastructure.repository.OrderRepository;
import com.example.demo.modules.cache.generatekey.timestampmismatch.infrastructure.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("timestampCacheOrderServiceImpl")
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    
    @Override
    @Cacheable(value = "orders", key = "#request.generateCacheKeyWithTimestamp()")  // PROBLEM: Timestamp in cache key
    public List<OrderDTO> getOrdersByCustomerWithTimestampProblem(OrderSearchRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("=== TIMESTAMP CACHE KEY PROBLEM DEMONSTRATION ===");
        
        // Log request details in structured format
        log.info("Request: customer={}, status={}, timestamp={}", 
                request.getCustomerName(), request.getStatus(), System.currentTimeMillis());
        
        // Demonstrate the problem: timestamp changes every call
        String cacheKey1 = request.generateCacheKeyWithTimestamp();
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        String cacheKey2 = request.generateCacheKeyWithTimestamp();
        
        log.info("PROBLEM: Same data produces different cache keys: '{}' vs '{}'", cacheKey1, cacheKey2);
        log.info("Cache miss every time due to timestamp changes!");
        
        // Execute database query
        List<Order> orders = orderRepository.findByCustomerNameAndStatus(request.getCustomerName(), request.getStatus());
        long endTime = System.currentTimeMillis();
        
        log.info("Database query executed - Retrieved {} orders for customer: {} in {}ms", 
                orders.size(), request.getCustomerName(), (endTime - startTime));
        
        return orderMapper.toDTOList(orders);
    }
    
    @Override
    @Cacheable(value = "orders", key = "#request.generateCacheKeyWithoutTimestamp()")  // SOLUTION: No timestamp
    public List<OrderDTO> getOrdersByCustomerWithoutTimestamp(OrderSearchRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("=== TIMESTAMP-FREE CACHE KEY SOLUTION ===");
        
        // Generate and log cache key
        String cacheKey = request.generateCacheKeyWithoutTimestamp();
        log.info("Generated cache key (no timestamp): '{}' for customer: {}", cacheKey, request.getCustomerName());
        
        // Demonstrate the solution: same data, same cache key
        String sameDataCacheKey = request.generateCacheKeyWithoutTimestamp();
        log.info("SOLUTION: Same data produces same cache key: '{}' (match: {})", 
                sameDataCacheKey, cacheKey.equals(sameDataCacheKey));
        
        // Execute database query
        List<Order> orders = orderRepository.findByCustomerNameAndStatus(request.getCustomerName(), request.getStatus());
        long endTime = System.currentTimeMillis();
        
        log.info("Database query executed - Retrieved {} orders for customer: {} in {}ms", 
                orders.size(), request.getCustomerName(), (endTime - startTime));
        
        return orderMapper.toDTOList(orders);
    }
    
    @Override
    @Cacheable(value = "orders", key = "#request.generateTimeBucketedCacheKey()")  // SOLUTION: Time-bucketed
    public List<OrderDTO> getOrdersByCustomerWithTimeBucketing(OrderSearchRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("=== TIME-BUCKETED CACHE KEY SOLUTION ===");
        
        // Generate and log cache key
        String cacheKey = request.generateTimeBucketedCacheKey();
        log.info("Generated time-bucketed cache key: '{}' for customer: {}", cacheKey, request.getCustomerName());
        
        // Demonstrate the solution: same day, same cache key
        String sameDayCacheKey = request.generateTimeBucketedCacheKey();
        log.info("SOLUTION: Same day produces same cache key: '{}' (match: {})", 
                sameDayCacheKey, cacheKey.equals(sameDayCacheKey));
        
        // Execute database query
        List<Order> orders = orderRepository.findByCustomerNameAndStatus(request.getCustomerName(), request.getStatus());
        long endTime = System.currentTimeMillis();
        
        log.info("Database query executed - Retrieved {} orders for customer: {} in {}ms", 
                orders.size(), request.getCustomerName(), (endTime - startTime));
        
        return orderMapper.toDTOList(orders);
    }
    

        
    @Override
    @CacheEvict(value = "orders", allEntries = true)
    public void clearAllCaches() {
        log.info("=== CACHE CLEAR OPERATION ===");
        log.info("Clearing all order caches - All entries will be evicted");
    }
}
