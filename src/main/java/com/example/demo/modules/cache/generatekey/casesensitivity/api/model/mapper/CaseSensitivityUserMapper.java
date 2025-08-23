package com.example.demo.modules.cache.generatekey.casesensitivity.api.model.mapper;

import com.example.demo.modules.cache.generatekey.casesensitivity.domain.entity.CaseSensitivityUser;
import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.response.CaseSensitivityUserDTO;
import com.example.demo.modules.cache.generatekey.casesensitivity.api.model.dto.request.CaseSensitivitySearchRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaseSensitivityUserMapper {
    
    public CaseSensitivityUserDTO toDTO(CaseSensitivityUser entity) {
        if (entity == null) {
            return null;
        }
        
        return new CaseSensitivityUserDTO(
            entity.getId(),
            entity.getUserName(),
            entity.getCategory(),
            entity.getDescription(),
            entity.getRegion(),
            entity.getActive(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    public List<CaseSensitivityUserDTO> toDTOList(List<CaseSensitivityUser> entities) {
        if (entities == null) {
            return List.of();
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public CaseSensitivityUser toEntity(CaseSensitivitySearchRequest request, String description) {
        if (request == null) {
            return null;
        }
        
        return new CaseSensitivityUser(
            request.getUserName(),
            request.getCategory(),
            description,
            request.getRegion()
        );
    }
    
    // Helper method to create sample data based on request
    public CaseSensitivityUser createSampleEntity(CaseSensitivitySearchRequest request) {
        String description = String.format("Sample user for case sensitivity testing: %s in %s region", 
            request.getUserName(), request.getRegion());
        
        return toEntity(request, description);
    }
}
