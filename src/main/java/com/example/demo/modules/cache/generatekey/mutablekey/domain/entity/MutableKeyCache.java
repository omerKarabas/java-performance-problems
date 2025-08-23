package com.example.demo.modules.cache.generatekey.mutablekey.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "mutable_key_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutableKeyCache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String tag;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructor for demonstration
    public MutableKeyCache(String category, String tag, String description) {
        this.category = category;
        this.tag = tag;
        this.description = description;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructor with ID for mapper
    public MutableKeyCache(Long id, String category, String tag, String description) {
        this.id = id;
        this.category = category;
        this.tag = tag;
        this.description = description;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
