package com.example.demo.modules.cache.generatekey.mutablekey.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.mutablekey.domain.entity.MutableKeyCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutableKeyRepository extends JpaRepository<MutableKeyCache, Long> {
    
    /**
     * Find cache entries by category and tag combinations
     * Used to demonstrate the mutable key problem
     */
    @Query("SELECT m FROM MutableKeyCache m WHERE " +
           "(:categories IS NULL OR m.category IN :categories) AND " +
           "(:tags IS NULL OR m.tag IN :tags) AND " +
           "m.active = true")
    List<MutableKeyCache> findByCategoriesAndTags(
        @Param("categories") List<String> categories, 
        @Param("tags") List<String> tags
    );
    
    /**
     * Find by single category for simple search
     */
    List<MutableKeyCache> findByCategoryAndActiveTrue(String category);
    
    /**
     * Find by single tag for simple search
     */
    List<MutableKeyCache> findByTagAndActiveTrue(String tag);
}
