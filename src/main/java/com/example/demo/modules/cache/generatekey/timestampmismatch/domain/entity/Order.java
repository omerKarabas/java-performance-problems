package com.example.demo.modules.cache.generatekey.timestampmismatch.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "timestamp_cache_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String orderNumber;
    
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String productName;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private LocalDateTime orderDate;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private Boolean active;
    
    // Constructor for demonstration
    public Order(String orderNumber, String customerName, String productName, BigDecimal amount, Integer quantity, String status) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.productName = productName;
        this.amount = amount;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.status = status;
        this.active = true;
    }
    
    // Constructor with ID for mapper
    public Order(Long id, String orderNumber, String customerName, String productName, BigDecimal amount, Integer quantity, String status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.productName = productName;
        this.amount = amount;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.status = status;
        this.active = true;
    }
}
