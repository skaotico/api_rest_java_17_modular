package com.skaotico.servicio.rest.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la respuesta de autenticación.
 * Contiene el token JWT y la información básica del usuario autenticado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    /**
     * Token JWT generado para el usuario.
     */
    private String token;

    /**
     * Correo electrónico del usuario autenticado.
     */
    private String email;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;


}
