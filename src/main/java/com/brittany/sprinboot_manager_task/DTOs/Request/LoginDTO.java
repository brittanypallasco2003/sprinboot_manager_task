package com.brittany.sprinboot_manager_task.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "El email es obligatorio") @Email(message = "El correo electrónico no es válido") String email,
        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres") String password) {

}
