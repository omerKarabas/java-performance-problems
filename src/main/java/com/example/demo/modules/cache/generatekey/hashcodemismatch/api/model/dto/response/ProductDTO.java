package com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.response;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private Long id;
    private String code;
    private String name;
    private ProductCategory category;
    private BigDecimal price;
    private Integer stockQuantity;
    private Boolean active;
}
