package com.example.demo.modules.nplusone.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nplusone_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NPlusOneOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderNumber;
    private Double amount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nplusone_user_id")
    private NPlusOneUser user;
}
