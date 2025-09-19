package com.brittany.sprinboot_manager_task.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brittany.sprinboot_manager_task.DTOs.Response.ErrorResponseDTO;
import com.brittany.sprinboot_manager_task.utils.ErrorResponseFactory;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    public GlobalExceptionHandler(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDTO response = errorResponseFactory.build(errorStatus.value(), errorStatus.name(), ex.getMessage(),
                request.getRequestURI(), null);

        return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;
        // ErrorResponseDTO response = mapErrorResponseDTO(errorStatus.value(),
        // errorStatus.name(), ex.getMessage(),
        // request.getRequestURI(),
        // ex.getBindingResult()
        // .getFieldErrors()
        // .stream()
        // .map(fieldError -> fieldError.getField() + ": " +
        // fieldError.getDefaultMessage())
        // .collect(Collectors.toList()));

        ErrorResponseDTO response = errorResponseFactory.build(errorStatus.value(), errorStatus.name(), ex.getMessage(),
                request.getRequestURI(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map((fieldE) -> fieldE.getField() + " : " + fieldE.getDefaultMessage())
                        .collect(Collectors.toList()));

        return new ResponseEntity<>(response, errorStatus);
    }
}
