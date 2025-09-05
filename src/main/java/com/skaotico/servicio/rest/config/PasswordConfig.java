package com.skaotico.servicio.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración para el manejo de encriptación de contraseñas.
 * Define un bean para BCryptPasswordEncoder.
 */
@Configuration
public class PasswordConfig {

    /**
     * Crea un bean de BCryptPasswordEncoder para encriptar contraseñas.
     *
     * @return BCryptPasswordEncoder para uso en servicios.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
