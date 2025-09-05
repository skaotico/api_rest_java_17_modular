package com.skaotico.servicio.rest.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir información de login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    /**
     * Email del usuario.
     * Debe ser un email válido y no estar vacío.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    /**
     * Contraseña del usuario.
     * No puede estar vacía y debe tener entre 6 y 255 caracteres.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
    private String password;
}
