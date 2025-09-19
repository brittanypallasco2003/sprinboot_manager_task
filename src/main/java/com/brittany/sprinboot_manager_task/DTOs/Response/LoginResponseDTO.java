package com.brittany.sprinboot_manager_task.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "email", "mensaje", "jwt" })
public record LoginResponseDTO(String email, String mensaje, String jwt) {

}
