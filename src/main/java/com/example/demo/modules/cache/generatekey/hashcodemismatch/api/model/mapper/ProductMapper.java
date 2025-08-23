package com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.mapper;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.api.model.dto.response.ProductDTO;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getActive()
        );
    }
    
    public List<ProductDTO> toDTOList(List<Product> products) {
        if (products == null) {
            return null;
        }
        
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        
        return new Product(
                productDTO.getId(),
                productDTO.getCode(),
                productDTO.getName(),
                productDTO.getCategory(),
                productDTO.getPrice(),
                productDTO.getStockQuantity(),
                productDTO.getActive()
        );
    }
}
