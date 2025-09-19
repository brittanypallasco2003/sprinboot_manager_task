package com.brittany.sprinboot_manager_task.DTOs.Request;

import com.brittany.sprinboot_manager_task.models.EstadoTareaEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TareaRequestDTO(
                @NotBlank(message = "El título es obligatorio") @Size(min = 4, max = 20, message = "El título debe tener entre 4 y 20 caracteres") String titulo,
                @NotBlank(message = "La descripción es obligatoria") @Size(max = 150, message = "La descripción puede tener un máximo de 150 caracteres")String descripcion,
                @NotNull(message = "El estado de la tarea no puede ser null") EstadoTareaEnum estado) {
}
