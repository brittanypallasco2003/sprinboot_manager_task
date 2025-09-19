package com.brittany.sprinboot_manager_task.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.brittany.sprinboot_manager_task.DTOs.Response.ErrorResponseDTO;

@Component
public class ErrorResponseFactory {
    public ErrorResponseDTO build(int status, String error, String message, String path, List<String> details) {
        return ErrorResponseDTO.builder()
                .status(status)
                .error(error)
                .mensaje(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .details(details)
                .build();
    }

}
