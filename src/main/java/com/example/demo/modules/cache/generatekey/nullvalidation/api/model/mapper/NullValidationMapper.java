package com.example.demo.modules.cache.generatekey.nullvalidation.api.model.mapper;

import com.example.demo.modules.cache.generatekey.nullvalidation.api.model.dto.response.NullValidationDTO;
import com.example.demo.modules.cache.generatekey.nullvalidation.domain.entity.NullValidationCache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NullValidationMapper {
    
    /**
     * Converts entity to DTO
     */
    public NullValidationDTO toDTO(NullValidationCache entity) {
        if (entity == null) {
            return null;
        }
        
        return new NullValidationDTO(
            entity.getId(),
            entity.getUserId(),
            entity.getProductId(),
            entity.getCategoryId(),
            entity.getDescription(),
            entity.getActive(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    /**
     * Converts list of entities to list of DTOs
     */
    public List<NullValidationDTO> toDTOList(List<NullValidationCache> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                      .map(this::toDTO)
                      .collect(Collectors.toList());
    }
    
    /**
     * Converts DTO to entity
     */
    public NullValidationCache toEntity(NullValidationDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new NullValidationCache(
            dto.getId(),
            dto.getUserId(),
            dto.getProductId(),
            dto.getCategoryId(),
            dto.getDescription()
        );
    }
}
