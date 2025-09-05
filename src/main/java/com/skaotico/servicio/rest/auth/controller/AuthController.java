package com.skaotico.servicio.rest.auth.controller;


import com.skaotico.servicio.rest.auth.dto.AuthResponseDto;
import com.skaotico.servicio.rest.auth.dto.LoginDto;
import com.skaotico.servicio.rest.auth.service.AuthService;
import com.skaotico.servicio.rest.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador de autenticación para gestionar login, logout y registro de usuarios.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


@Autowired
private AuthService authService;

    /**
     * Realiza el login de un usuario y genera un token JWT.
     *
     * @param request DTO con email y contraseña.
     * @return ResponseEntity con token y datos del usuario o error 401.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        AuthResponseDto token =   this.authService.login(loginDto);


        return ResponseEntity.ok(token);
    }

    /**
     * Realiza el logout de un usuario.
     *
     * @return ResponseEntity indicando logout exitoso.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout exitoso");
    }


}
