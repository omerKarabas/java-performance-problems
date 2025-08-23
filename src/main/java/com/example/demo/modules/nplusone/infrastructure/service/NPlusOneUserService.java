package com.example.demo.modules.nplusone.infrastructure.service;

import com.example.demo.modules.nplusone.api.dto.NPlusOneUserDTO;

import java.util.List;

public interface NPlusOneUserService {
    
    List<NPlusOneUserDTO> getUsersWithOrdersNPlusOne();
    
    List<NPlusOneUserDTO> getUsersWithOrdersOptimized();
}
