package com.brittany.sprinboot_manager_task.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brittany.sprinboot_manager_task.DTOs.Request.LoginDTO;
import com.brittany.sprinboot_manager_task.DTOs.Request.RegisterRequestDTO;
import com.brittany.sprinboot_manager_task.services.AuthServiceI;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceI authServiceI;

    public AuthController(AuthServiceI authServiceI) {
        this.authServiceI = authServiceI;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequestDTO dto) {
        return new ResponseEntity<>(authServiceI.registerUser(dto),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDTO dto) {
        return new ResponseEntity<>(authServiceI.loginUser(dto),HttpStatus.OK);
    }
    
}
