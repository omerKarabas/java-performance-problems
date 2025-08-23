package com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchRequest {
    
    private String customerName;
    private String productName;
    private String status;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    
    // Constructor for demonstration
    public OrderSearchRequest(String customerName, String status) {
        this.customerName = customerName;
        this.productName = null;
        this.status = status;
        this.minAmount = null;
        this.maxAmount = null;
        this.fromDate = null;
        this.toDate = null;
    }
    
    // PROBLEM: Timestamp-based cache key generation
    public String generateCacheKeyWithTimestamp() {
        return "customer:" + (customerName != null ? customerName : "null") +
               "|status:" + (status != null ? status : "null") +
               "|timestamp:" + System.currentTimeMillis(); // PROBLEM: Every call generates different key
    }
    
    // SOLUTION: Timestamp-free cache key generation
    public String generateCacheKeyWithoutTimestamp() {
        return "customer:" + (customerName != null ? customerName : "null") +
               "|status:" + (status != null ? status : "null");
    }
    
    // SOLUTION: Time-bucketed cache key (cache by day - start of day)
    public String generateTimeBucketedCacheKey() {
        // Get current date and round to start of day (00:00:00)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        long dayTimestamp = startOfDay.toEpochSecond(java.time.ZoneOffset.UTC);
        
        return "customer:" + (customerName != null ? customerName : "null") +
               "|status:" + (status != null ? status : "null") +
               "|day:" + dayTimestamp;
    }
    

}
