package com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.mapper;

import com.example.demo.modules.cache.generatekey.stringconcatenation.domain.entity.StringConcatUser;
import com.example.demo.modules.cache.generatekey.stringconcatenation.api.model.dto.response.StringConcatDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringConcatMapper {
    
    public StringConcatDTO toDTO(StringConcatUser entity) {
        if (entity == null) {
            return null;
        }
        
        return new StringConcatDTO(
            entity.getId(),
            entity.getUserId(),
            entity.getProductId(),
            entity.getProductName(),
            entity.getPrice(),
            entity.getQuantity(),
            entity.getCreatedAt(),
            entity.getActive()
        );
    }
    
    public List<StringConcatDTO> toDTOList(List<StringConcatUser> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public StringConcatUser toEntity(StringConcatDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new StringConcatUser(
            dto.getId(),
            dto.getUserId(),
            dto.getProductId(),
            dto.getProductName(),
            dto.getPrice(),
            dto.getQuantity()
        );
    }
}
