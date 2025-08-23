package com.example.demo.modules.nplusone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    private String username;
    private String email;
    private List<OrderDTO> orders;
    private int orderCount;
}
