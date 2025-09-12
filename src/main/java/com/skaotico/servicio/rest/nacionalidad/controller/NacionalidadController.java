package com.skaotico.servicio.rest.nacionalidad.controller;


import com.skaotico.servicio.rest.nacionalidad.dto.NacionalidadDto;
import com.skaotico.servicio.rest.nacionalidad.service.NacionalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nacionalidades")
public class NacionalidadController {


    private final NacionalidadService nacionalidadService;

    public NacionalidadController(NacionalidadService nacionalidadService) {
        this.nacionalidadService = nacionalidadService;
    }


    /**
     * Endpoint para listar todas las nacionalidades.
     *
     * @return Lista de Nacionalidad DTOs
     */
    @GetMapping
    public List<NacionalidadDto> listarNacionalidades() {
        return nacionalidadService.listarNacionalidades();
    }


    @PostMapping("/nacionalidades/masivas")
    public ResponseEntity<List<NacionalidadDto>> insertarMasivas(
            @RequestBody List<NacionalidadDto> nacionalidades) {
        var result = nacionalidadService.insertarNacionalidadesMasivas(nacionalidades);
        return ResponseEntity.ok(result);
    }
}
