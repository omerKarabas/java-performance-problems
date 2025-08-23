package com.example.demo.modules.cache.generatekey.stringconcatenation.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.stringconcatenation.domain.entity.StringConcatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StringConcatRepository extends JpaRepository<StringConcatUser, Long> {
    
    @Query("SELECT s FROM StringConcatUser s WHERE s.userId = :userId AND s.productId = :productId AND s.active = true")
    List<StringConcatUser> findByUserIdAndProductId(@Param("userId") String userId, @Param("productId") String productId);
    
    @Query("SELECT s FROM StringConcatUser s WHERE s.userId LIKE %:userId% AND s.active = true")
    List<StringConcatUser> findByUserIdContaining(@Param("userId") String userId);
    
    @Query("SELECT s FROM StringConcatUser s WHERE s.active = true ORDER BY s.createdAt DESC")
    List<StringConcatUser> findAllActiveOrderedByCreatedAt();
}
