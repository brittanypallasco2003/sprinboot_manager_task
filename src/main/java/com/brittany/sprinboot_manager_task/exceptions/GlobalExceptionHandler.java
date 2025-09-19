package com.brittany.sprinboot_manager_task.exceptions;

import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.brittany.sprinboot_manager_task.DTOs.Response.ErrorResponseDTO;
import com.brittany.sprinboot_manager_task.utils.ErrorResponseFactory;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    public GlobalExceptionHandler(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    @ExceptionHandler(Exception.class)
    @Order(20)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDTO response = errorResponseFactory.build(errorStatus.value(), errorStatus.name(), ex.getMessage(),
                request.getRequestURI(), null);

        return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException ex,
                                                                           HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDTO response = errorResponseFactory.build(
                status.value(),
                status.name(),
                "Credenciales inválidas o no proporcionadas",
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;
        String message = "Error al leer el cuerpo de la solicitud. Verifique el formato del JSON.";

        ErrorResponseDTO response = errorResponseFactory.build(
                errorStatus.value(),
                errorStatus.name(),
                message,
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO response = errorResponseFactory.build(errorStatus.value(), errorStatus.name(), "Error de Validación en los campos",
                request.getRequestURI(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map((fieldE) -> fieldE.getField() + " : " + fieldE.getDefaultMessage())
                        .collect(Collectors.toList()));

        return new ResponseEntity<>(response, errorStatus);
    }
}
