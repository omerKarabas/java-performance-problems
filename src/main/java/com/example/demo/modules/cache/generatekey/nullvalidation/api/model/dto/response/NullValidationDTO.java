package com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NullValidationDTO {
    
    private Long id;
    private String userId;
    private String productId;
    private String categoryId;
    private String description;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
