package com.example.demo.modules.nplusone.service;

import com.example.demo.modules.nplusone.dto.UserDTO;
import java.util.List;

public interface UserService {
    
    List<UserDTO> getUsersWithOrdersNPlusOne();
    
    List<UserDTO> getUsersWithOrdersOptimized();
}
