package com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long id;
    private String orderNumber;
    private String customerName;
    private String productName;
    private BigDecimal amount;
    private Integer quantity;
    private LocalDateTime orderDate;
    private String status;
    private Boolean active;
}
