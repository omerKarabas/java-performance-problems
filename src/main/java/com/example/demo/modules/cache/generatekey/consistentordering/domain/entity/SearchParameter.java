package com.example.demo.modules.cache.generatekey.consistentordering.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_parameter_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private String productName;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minPrice;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maxPrice;
    
    @Column(nullable = false)
    private String region;
    
    @Column(nullable = false)
    private Integer rating;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private Boolean active;
    
    // Constructor for demonstration
    public SearchParameter(String category, String brand, String productName, BigDecimal minPrice, BigDecimal maxPrice, String region, Integer rating) {
        this.category = category;
        this.brand = brand;
        this.productName = productName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.region = region;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
    
    // Constructor with ID for mapper
    public SearchParameter(Long id, String category, String brand, String productName, BigDecimal minPrice, BigDecimal maxPrice, String region, Integer rating) {
        this.id = id;
        this.category = category;
        this.brand = brand;
        this.productName = productName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.region = region;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
