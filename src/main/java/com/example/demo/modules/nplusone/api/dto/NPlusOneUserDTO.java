package com.example.demo.modules.nplusone.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NPlusOneUserDTO {
    private Long id;
    private String username;
    private String email;
    private List<NPlusOneOrderDTO> orders;
}
