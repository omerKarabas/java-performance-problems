package com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseSensitivityUserDTO {
    
    private Long id;
    private String userName;
    private String category;
    private String description;
    private String region;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
