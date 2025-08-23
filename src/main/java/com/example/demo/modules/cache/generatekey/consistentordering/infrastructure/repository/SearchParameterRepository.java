package com.example.demo.modules.cache.generatekey.consistentordering.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.consistentordering.domain.entity.SearchParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SearchParameterRepository extends JpaRepository<SearchParameter, Long> {
    
    @Query("SELECT s FROM SearchParameter s WHERE " +
           "(:category IS NULL OR s.category = :category) AND " +
           "(:brand IS NULL OR s.brand = :brand) AND " +
           "(:region IS NULL OR s.region = :region) AND " +
           "s.minPrice <= :maxPrice AND s.maxPrice >= :minPrice AND " +
           "s.rating >= :minRating AND s.active = true")
    List<SearchParameter> findBySearchCriteria(
        @Param("category") String category,
        @Param("brand") String brand,
        @Param("region") String region,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("minRating") Integer minRating
    );
    
    @Query("SELECT s FROM SearchParameter s WHERE s.category = :category AND s.active = true ORDER BY s.createdAt DESC")
    List<SearchParameter> findByCategoryOrderedByCreatedAt(@Param("category") String category);
    
    @Query("SELECT s FROM SearchParameter s WHERE s.active = true ORDER BY s.rating DESC, s.createdAt DESC")
    List<SearchParameter> findAllActiveOrderedByRatingAndCreatedAt();
}
