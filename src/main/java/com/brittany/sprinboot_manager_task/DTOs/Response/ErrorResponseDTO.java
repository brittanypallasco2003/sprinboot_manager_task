package com.brittany.sprinboot_manager_task.DTOs.Response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
    private int status;
    private String error;
    private String mensaje;
    private String path;
    private LocalDateTime timestamp;
    private List<String> details;
}
