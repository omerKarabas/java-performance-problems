package com.example.demo.modules.cache.generatekey.mutablekey.api.model.mapper;

import com.example.demo.modules.cache.generatekey.mutablekey.api.model.dto.response.MutableKeyDTO;
import com.example.demo.modules.cache.generatekey.mutablekey.domain.entity.MutableKeyCache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MutableKeyMapper {
    
    /**
     * Converts entity to DTO
     */
    public MutableKeyDTO toDTO(MutableKeyCache entity) {
        if (entity == null) {
            return null;
        }
        
        return new MutableKeyDTO(
            entity.getId(),
            entity.getCategory(),
            entity.getTag(),
            entity.getDescription(),
            entity.getActive(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    /**
     * Converts list of entities to list of DTOs
     */
    public List<MutableKeyDTO> toDTOList(List<MutableKeyCache> entities) {
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
    public MutableKeyCache toEntity(MutableKeyDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new MutableKeyCache(
            dto.getId(),
            dto.getCategory(),
            dto.getTag(),
            dto.getDescription()
        );
    }
}
