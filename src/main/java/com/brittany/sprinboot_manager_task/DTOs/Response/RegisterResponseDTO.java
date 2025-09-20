package com.brittany.sprinboot_manager_task.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "mensaje", "id", "nombre", "email" })
public record RegisterResponseDTO(String mensaje, Long id, String nombre, String email) {

}
