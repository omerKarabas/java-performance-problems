package com.example.demo.modules.cache.generatekey.stringconcatenation.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "string_concat_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringConcatUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private String productName;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private Boolean active;
    
    // Constructor for demonstration
    public StringConcatUser(String userId, String productId, String productName, BigDecimal price, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
    
    // Constructor with ID for mapper
    public StringConcatUser(Long id, String userId, String productId, String productName, BigDecimal price, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
