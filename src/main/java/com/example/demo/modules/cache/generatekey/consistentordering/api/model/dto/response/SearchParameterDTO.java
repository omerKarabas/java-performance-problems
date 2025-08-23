package com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParameterDTO {
    
    private Long id;
    private String category;
    private String brand;
    private String productName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String region;
    private Integer rating;
    private LocalDateTime createdAt;
    private Boolean active;
}
