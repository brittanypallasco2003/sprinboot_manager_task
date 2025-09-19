package com.brittany.sprinboot_manager_task.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.brittany.sprinboot_manager_task.DTOs.Response.ErrorResponseDTO;
import com.brittany.sprinboot_manager_task.utils.ErrorResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
     private final ObjectMapper mapper;
    private final ErrorResponseFactory factory;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper, ErrorResponseFactory factory) {
        this.mapper = mapper;
        this.factory = factory;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {

        ErrorResponseDTO error = factory.build(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(),
                "Credenciales inválidas o no proporcionadas",
                request.getRequestURI(),
                null
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        mapper.writeValue(response.getWriter(), error);
    }

}
