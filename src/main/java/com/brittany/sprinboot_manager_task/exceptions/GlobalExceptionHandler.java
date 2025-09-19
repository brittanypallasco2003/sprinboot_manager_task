package com.brittany.sprinboot_manager_task.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brittany.sprinboot_manager_task.DTOs.Response.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus error = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDTO response = mapErrorResponseDTO(error.value(), error.name(), ex.getMessage(),
                request.getRequestURI(), null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        HttpStatus error = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO response = mapErrorResponseDTO(error.value(), error.name(), ex.getMessage(),
                request.getRequestURI(),
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO mapErrorResponseDTO(int status, String error, String message, String path,
            List<String> details) {
        return ErrorResponseDTO.builder()
                .status(status)
                .mensaje(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .details(details)
                .build();
    }
}
