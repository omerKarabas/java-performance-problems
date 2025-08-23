package com.example.demo.modules.cache.generatekey.casesensitivity.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "case_sensitivity_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseSensitivityUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String userName;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String region;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructor for demonstration
    public CaseSensitivityUser(String userName, String category, String description, String region) {
        this.userName = userName;
        this.category = category;
        this.description = description;
        this.region = region;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Constructor with ID for mapper
    public CaseSensitivityUser(Long id, String userName, String category, String description, String region) {
        this.id = id;
        this.userName = userName;
        this.category = category;
        this.description = description;
        this.region = region;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
