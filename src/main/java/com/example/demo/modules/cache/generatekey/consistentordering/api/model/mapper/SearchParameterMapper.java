package com.example.demo.modules.cache.generatekey.consistentordering.api.model.mapper;

import com.example.demo.modules.cache.generatekey.consistentordering.domain.entity.SearchParameter;
import com.example.demo.modules.cache.generatekey.consistentordering.api.model.dto.response.SearchParameterDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchParameterMapper {
    
    public SearchParameterDTO toDTO(SearchParameter entity) {
        if (entity == null) {
            return null;
        }
        
        return new SearchParameterDTO(
            entity.getId(),
            entity.getCategory(),
            entity.getBrand(),
            entity.getProductName(),
            entity.getMinPrice(),
            entity.getMaxPrice(),
            entity.getRegion(),
            entity.getRating(),
            entity.getCreatedAt(),
            entity.getActive()
        );
    }
    
    public List<SearchParameterDTO> toDTOList(List<SearchParameter> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public SearchParameter toEntity(SearchParameterDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new SearchParameter(
            dto.getId(),
            dto.getCategory(),
            dto.getBrand(),
            dto.getProductName(),
            dto.getMinPrice(),
            dto.getMaxPrice(),
            dto.getRegion(),
            dto.getRating()
        );
    }
}
