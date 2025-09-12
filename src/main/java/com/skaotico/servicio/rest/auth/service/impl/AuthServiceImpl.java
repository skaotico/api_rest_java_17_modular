package com.skaotico.servicio.rest.auth.service.impl;

import com.skaotico.servicio.rest.auth.dto.AuthResponseDto;
import com.skaotico.servicio.rest.auth.dto.LoginDto;
import com.skaotico.servicio.rest.auth.dto.LoginResponseDto;
import com.skaotico.servicio.rest.auth.service.AuthService;

import com.skaotico.servicio.rest.usuario.model.Usuario;

import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import com.skaotico.servicio.rest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación que se encarga del login de usuarios y generación de JWT.
 */
@Service
public class AuthServiceImpl implements AuthService  {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Autentica a un usuario en el sistema mediante sus credenciales.
     * <p>
     * Este método busca al usuario por su email, valida que la contraseña ingresada
     * coincida con el hash almacenado en la base de datos y, en caso exitoso,
     * genera un token JWT que identifica al usuario autenticado.
     * </p>
     *
     * @param loginDto DTO que contiene el email y la contraseña en texto plano.
     * @return {@link AuthResponseDto} con el token JWT y los datos básicos del usuario.
     *
     * @throws RuntimeException si:
     * <ul>
     *   <li>El usuario no existe en el sistema.</li>
     *   <li>La contraseña no coincide con la almacenada.</li>
     * </ul>
     */
    public AuthResponseDto login(LoginDto loginDto) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(loginDto.getEmail());
        System.out.println("usuario encontrado " + usuario);

        if (!passwordEncoder.matches(loginDto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(usuario);



        return AuthResponseDto.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .build();
    }

    /**
     * Realiza el logout de un usuario.
     * Actualmente, el logout no requiere lógica en el backend ya que JWT es stateless.
     * En caso de implementar un blacklist o revocación de tokens, se podría agregar aquí.
     */
    public void logout() {
        // Aquí podría agregarse lógica de blacklist de tokens si se requiere
    }


}
