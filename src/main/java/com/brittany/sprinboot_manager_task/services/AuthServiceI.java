package com.brittany.sprinboot_manager_task.services;

import com.brittany.sprinboot_manager_task.DTOs.Request.LoginDTO;
import com.brittany.sprinboot_manager_task.DTOs.Request.RegisterRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.LoginResponseDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.RegisterResponseDTO;

public interface AuthServiceI {
    LoginResponseDTO loginUser(LoginDTO dto);
    RegisterResponseDTO registerUser(RegisterRequestDTO dto);
}
