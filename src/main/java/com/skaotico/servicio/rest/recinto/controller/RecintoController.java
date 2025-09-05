package com.skaotico.servicio.rest.recinto.controller;

import com.skaotico.servicio.rest.recinto.dto.RecintoCreateDTO;
import com.skaotico.servicio.rest.recinto.service.RecintoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Recintos.
 *
 * Proporciona endpoints para crear, consultar, actualizar y eliminar recintos.
 */
@RestController
@RequestMapping("/recinto")
@Tag(name = "Recintos", description = "API para gestión de recintos")
public class RecintoController {

   @Autowired
    private RecintoService recintoService;


    @PostMapping
    @Operation(summary = "Crear un nuevo recinto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recinto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<com.skaotico.servicio.rest.recinto.model.Recinto> create(@Valid @RequestBody RecintoCreateDTO recientoCreto) {

        com.skaotico.servicio.rest.recinto.model.Recinto recintoCreado = this.recintoService.crearRecinto(recientoCreto);
        return ResponseEntity.status(201).body(recintoCreado);
    }


    /**
     * Obtiene todos los recintos registrados.
     *
     * @return ResponseEntity con la lista de recintos y código HTTP 200.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los recintos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recintos obtenida correctamente")
    })
    public ResponseEntity<List<com.skaotico.servicio.rest.recinto.model.Recinto>> findAll() {
        List<com.skaotico.servicio.rest.recinto.model.Recinto> recintos = recintoService.listarTodosRecintos();
        return ResponseEntity.ok(recintos);
    }

    /**
     * Obtiene un recinto específico por su ID.
     *
     * @param id ID del recinto a consultar.
     * @return ResponseEntity con el recinto encontrado y código HTTP 200.
     * @throws ResourceNotFoundException si no existe un recinto con el ID proporcionado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un recinto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recinto encontrado"),
            @ApiResponse(responseCode = "404", description = "Recinto no encontrado")
    })
    public ResponseEntity<com.skaotico.servicio.rest.recinto.model.Recinto> findOne(@PathVariable Long id) {
         com.skaotico.servicio.rest.recinto.model.Recinto recinto = recintoService.obtenerRecintoPorId(id);
        return ResponseEntity.ok(recinto);
    }
}
