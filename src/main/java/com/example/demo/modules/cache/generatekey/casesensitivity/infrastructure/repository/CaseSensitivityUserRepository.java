package com.example.demo.modules.cache.generatekey.casesensitivity.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.casesensitivity.domain.entity.CaseSensitivityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseSensitivityUserRepository extends JpaRepository<CaseSensitivityUser, Long> {
    
    // Case-sensitive search (demonstrates the problem)
    List<CaseSensitivityUser> findByUserNameAndCategoryAndRegion(String userName, String category, String region);
    
    // Case-insensitive search (solution)
    @Query("SELECT u FROM CaseSensitivityUser u WHERE " +
           "LOWER(u.userName) = LOWER(:userName) AND " +
           "LOWER(u.category) = LOWER(:category) AND " +
           "LOWER(u.region) = LOWER(:region)")
    List<CaseSensitivityUser> findByUserNameAndCategoryAndRegionIgnoreCase(
        @Param("userName") String userName, 
        @Param("category") String category, 
        @Param("region") String region
    );
    
    // Find by username only (case-sensitive)
    List<CaseSensitivityUser> findByUserName(String userName);
    
    // Find by username only (case-insensitive)
    @Query("SELECT u FROM CaseSensitivityUser u WHERE LOWER(u.userName) = LOWER(:userName)")
    List<CaseSensitivityUser> findByUserNameIgnoreCase(@Param("userName") String userName);
    
    // Find by category (case-insensitive)
    @Query("SELECT u FROM CaseSensitivityUser u WHERE LOWER(u.category) = LOWER(:category)")
    List<CaseSensitivityUser> findByCategoryIgnoreCase(@Param("category") String category);
    
    // Find active users only
    List<CaseSensitivityUser> findByActiveTrue();
}
