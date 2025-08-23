package com.example.demo.modules.cache.generatekey.hashcodemismatch.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.Product;
import com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategory(ProductCategory category);
}
