package com.example.demo.modules.cache.generatekey.nullvalidation.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.nullvalidation.domain.entity.NullValidationCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NullValidationRepository extends JpaRepository<NullValidationCache, Long> {
    
    /**
     * Find cache entries by userId, productId and categoryId combinations
     * Used to demonstrate the null validation problem
     * Handles null values in the query
     */
    @Query("SELECT n FROM NullValidationCache n WHERE " +
           "(:userId IS NULL OR n.userId = :userId) AND " +
           "(:productId IS NULL OR n.productId = :productId) AND " +
           "(:categoryId IS NULL OR n.categoryId = :categoryId) AND " +
           "n.active = true")
    List<NullValidationCache> findByUserProductAndCategory(
        @Param("userId") String userId,
        @Param("productId") String productId,
        @Param("categoryId") String categoryId
    );
    
    /**
     * Find by productId only (productId should not be null)
     */
    List<NullValidationCache> findByProductIdAndActiveTrue(String productId);
    
    /**
     * Find by userId (can be null for guest users)
     */
    @Query("SELECT n FROM NullValidationCache n WHERE " +
           "(:userId IS NULL OR n.userId = :userId) AND " +
           "n.active = true")
    List<NullValidationCache> findByUserIdIncludingNull(@Param("userId") String userId);
    
    /**
     * Find by categoryId (can be null for general category)
     */
    @Query("SELECT n FROM NullValidationCache n WHERE " +
           "(:categoryId IS NULL OR n.categoryId = :categoryId) AND " +
           "n.active = true")
    List<NullValidationCache> findByCategoryIdIncludingNull(@Param("categoryId") String categoryId);
}
