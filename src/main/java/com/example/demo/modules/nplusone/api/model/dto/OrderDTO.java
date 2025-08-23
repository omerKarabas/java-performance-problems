package com.example.demo.modules.nplusone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long id;
    private String orderNumber;
    private BigDecimal amount;
}
