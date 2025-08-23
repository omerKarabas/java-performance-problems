package com.example.demo.modules.cache.generatekey.timestampmismatch.infrastructure.service;

import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.response.OrderDTO;
import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.request.OrderSearchRequest;

import java.util.List;

public interface OrderService {
    
    /**
     * Demonstrates the timestamp-based cache key problem
     * Uses System.currentTimeMillis() in cache key which causes cache misses
     */
    List<OrderDTO> getOrdersByCustomerWithTimestampProblem(OrderSearchRequest request);
    
    /**
     * Demonstrates the solution using timestamp-free cache key generation
     * Removes timestamp from cache key for consistent caching
     */
    List<OrderDTO> getOrdersByCustomerWithoutTimestamp(OrderSearchRequest request);
    
    /**
     * Demonstrates the time-bucketed cache key solution (start of day)
     * Uses day-based time buckets starting from 00:00:00 for time-sensitive caching
     */
    List<OrderDTO> getOrdersByCustomerWithTimeBucketing(OrderSearchRequest request);
    

    
    /**
     * Clears all caches for demonstration
     */
    void clearAllCaches();
}
