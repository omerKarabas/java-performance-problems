package com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringConcatDTO {
    
    private Long id;
    private String userId;
    private String productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdAt;
    private Boolean active;
}
