package com.example.demo.modules.cache.generatekey.nullvalidation.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "null_validation_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NullValidationCache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)  // Can be null to demonstrate the problem
    private String userId;
    
    @Column(nullable = false)
    private String productId;
    
    @Column(nullable = true)  // Can be null
    private String categoryId;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructor for demonstration
    public NullValidationCache(String userId, String productId, String categoryId, String description) {
        this.userId = userId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.description = description;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructor with ID for mapper
    public NullValidationCache(Long id, String userId, String productId, String categoryId, String description) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.description = description;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
