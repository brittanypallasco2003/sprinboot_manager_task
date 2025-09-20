package com.brittany.sprinboot_manager_task.exceptions;

import java.util.Arrays;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.brittany.sprinboot_manager_task.DTOs.Response.ErrorResponseDTO;
import com.brittany.sprinboot_manager_task.models.EstadoTareaEnum;
import com.brittany.sprinboot_manager_task.utils.ErrorResponseFactory;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(1)
public class TareaExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    public TareaExceptionHandler(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;
        String message;

        if (ex.getMessage() != null && ex.getMessage().contains("from String")) {
            message = buildEnumErrorMessage(ex.getMessage(), EstadoTareaEnum.class);
            ErrorResponseDTO response = errorResponseFactory.build(errorStatus.value(), errorStatus.name(), message,
                    request.getRequestURI(), null);
            return new ResponseEntity<>(response, errorStatus);
        }
        throw ex;

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex,
            HttpServletRequest request) {

        HttpStatus errorStatus = HttpStatus.FORBIDDEN;
        ErrorResponseDTO response = errorResponseFactory.build(errorStatus.value(), errorStatus.name(), ex.getMessage(),
                request.getRequestURI(), null);
        return new ResponseEntity<>(response, errorStatus);
    }

    private String buildEnumErrorMessage(String originalMessage, Class<? extends Enum<?>> enumClass) {
        String enumValues = String.join(", ",
                Arrays.stream(enumClass.getEnumConstants())
                        .map(Enum::name)
                        .toArray(String[]::new));
        return String.format("Valor inválido para el campo enum. Valores válidos: [%s]", enumValues);
    }

}
