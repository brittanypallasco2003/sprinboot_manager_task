package com.brittany.sprinboot_manager_task.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "mensaje", "email", "jwt" })
public record LoginResponseDTO(String mensaje, String email,String jwt) {

}
