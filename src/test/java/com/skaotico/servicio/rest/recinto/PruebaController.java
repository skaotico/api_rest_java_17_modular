package com.skaotico.servicio.rest.recinto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {

    @GetMapping("/hola")
    public String decirHola() {
        return "¡Hola desde mi servicio REST con Spring Boot y Gradle! 🚀";
    }
}
