package com.example.demo.modules.nplusone.controller;

import com.example.demo.modules.nplusone.dto.UserDTO;
import com.example.demo.modules.nplusone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nplusone")
@RequiredArgsConstructor
public class NPlusOneController {
    
    private final UserService userService;
    
    @GetMapping("/users/nplusone")
    public ResponseEntity<String> demonstrateNPlusOneProblem() {
        long startTime = System.currentTimeMillis();
        
        List<UserDTO> users = userService.getUsersWithOrdersNPlusOne();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        String result = String.format(
            "N+1 PROBLEM COMPLETED\n" +
            "Total time: %d ms\n" +
            "User count: %d\n" +
            "Total queries: %d (1 + %d = N+1)",
            duration, users.size(), users.size() + 1, users.size()
        );
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/users/optimized")
    public ResponseEntity<String> demonstrateOptimizedSolution() {
        long startTime = System.currentTimeMillis();
        
        List<UserDTO> users = userService.getUsersWithOrdersOptimized();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        String result = String.format(
            "OPTIMIZED SOLUTION COMPLETED\n" +
            "Total time: %d ms\n" +
            "User count: %d\n" +
            "Total queries: 1 (with JOIN FETCH)",
            duration, users.size()
        );
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/info")
    public ResponseEntity<String> getNPlusOneInfo() {
        String result = """
            N+1 QUERY PROBLEM EXPLANATION
            
            The N+1 problem is a common performance issue in database queries:
            
            1. First query: fetches N main records (e.g., all users)
            2. Next N queries: fetches related data for each main record separately
            
            EXAMPLE:
            - 100 users exist
            - First query: SELECT * FROM users (1 query)
            - Next queries: SELECT * FROM orders WHERE user_id = X for each user (100 queries)
            - TOTAL: 1 + 100 = 101 queries (N+1)
            
            SOLUTIONS:
            1. Use JOIN FETCH
            2. Use EntityGraph
            3. Batch processing
            4. Proper lazy loading usage
            
            This demo shows both approaches!
            """;
        
        return ResponseEntity.ok(result);
    }
}
