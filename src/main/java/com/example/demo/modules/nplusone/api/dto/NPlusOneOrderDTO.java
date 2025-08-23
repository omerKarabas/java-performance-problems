package com.example.demo.modules.nplusone.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NPlusOneOrderDTO {
    private Long id;
    private String orderNumber;
    private Double amount;
}
