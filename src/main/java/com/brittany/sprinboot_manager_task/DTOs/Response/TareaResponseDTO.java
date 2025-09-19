package com.brittany.sprinboot_manager_task.DTOs.Response;

import java.time.LocalDateTime;

import com.brittany.sprinboot_manager_task.models.EstadoTareaEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "userId", "titulo", "description", "estado", "createdAt", "updatedAt" })
public record TareaResponseDTO(Long userId, String titulo, String description, EstadoTareaEnum estado,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
}
