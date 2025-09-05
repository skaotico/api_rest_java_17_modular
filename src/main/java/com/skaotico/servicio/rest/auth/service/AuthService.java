package com.skaotico.servicio.rest.auth.service;

import com.skaotico.servicio.rest.auth.dto.AuthResponseDto;
import com.skaotico.servicio.rest.auth.dto.LoginDto;
import com.skaotico.servicio.rest.auth.dto.LoginResponseDto;

/**
 * Servicio de autenticación para manejar login de usuarios.
 */
public interface AuthService {

    /**
     * Realiza el login de un usuario.
     *
     * @param loginDto DTO con email y contraseña.
     * @return DTO con token y datos del usuario.
     */
    AuthResponseDto login(LoginDto loginDto);
}
