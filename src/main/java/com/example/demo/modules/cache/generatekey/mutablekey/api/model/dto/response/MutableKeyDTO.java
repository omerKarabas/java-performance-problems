package com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutableKeyDTO {
    
    private Long id;
    private String category;
    private String tag;
    private String description;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
