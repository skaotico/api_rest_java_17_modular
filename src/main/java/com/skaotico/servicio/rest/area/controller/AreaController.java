package com.skaotico.servicio.rest.area.controller;

import com.skaotico.servicio.rest.area.dto.Area;
import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;
import com.skaotico.servicio.rest.area.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@Tag(name = "Areas", description = "API para la gestión de áreas")
public class AreaController {

    private final AreaService areaService;

    @Operation(summary = "Crear un área", description = "Crea una nueva área con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody CreateAreaDTO createAreaDTO) {
        return ResponseEntity.ok(areaService.createArea(createAreaDTO));
    }

    @Operation(summary = "Obtener un área por ID", description = "Devuelve los datos de un área específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área encontrada"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Area> getArea(
            @Parameter(description = "ID del área a obtener") @PathVariable Long id) {
        return ResponseEntity.ok(areaService.getAreaById(id));
    }

    @Operation(summary = "Listar todas las áreas", description = "Devuelve todas las áreas registradas")
    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        return ResponseEntity.ok(areaService.getAllAreas());
    }

    @Operation(summary = "Actualizar un área", description = "Actualiza los datos de un área existente")
    @PutMapping("/{id}")
    public ResponseEntity<Area> updateArea(
            @Parameter(description = "ID del área a actualizar") @PathVariable Long id,
            @RequestBody Area area) {
        return ResponseEntity.ok(areaService.updateArea(id, area));
    }

    @Operation(summary = "Eliminar un área", description = "Elimina un área existente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(
            @Parameter(description = "ID del área a eliminar") @PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.noContent().build();
    }
}
