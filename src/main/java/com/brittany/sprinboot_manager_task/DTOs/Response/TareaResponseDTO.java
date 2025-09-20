package com.brittany.sprinboot_manager_task.DTOs.Response;

import java.time.LocalDateTime;

import com.brittany.sprinboot_manager_task.models.EstadoTareaEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "titulo", "descripcion", "estado", "createdAt", "updatedAt", "userId" })
public record TareaResponseDTO(Long id, String titulo, String description, EstadoTareaEnum estado,
        LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
}
