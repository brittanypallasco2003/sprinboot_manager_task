package com.brittany.sprinboot_manager_task.DTOs.Request;

import com.brittany.sprinboot_manager_task.validators.annotations.IsExistEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "El nombre es obligatorio") @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")String nombre,
        @NotBlank(message = "El email es obligatorio") @Email(message = "El correo electrónico no es válido") @IsExistEmail String email,
        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres") String password) {

}
