package com.skaotico.servicio.rest.rol.controller;

import com.skaotico.servicio.rest.rol.dto.RolDTO;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.rol.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rol")
@Tag(name = "Rol", description = "API para gestión de Roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/listar")
    @Operation(summary = "Obtener todos los roles")
    public ResponseEntity<List<RolModel>> listarRoles() {
        List<RolModel> lstRoles = this.rolService.obtenerTodosLosRoles();
        return ResponseEntity.ok(lstRoles);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo rol")
    public ResponseEntity<RolModel> crearRol(@Valid @RequestBody RolDTO rolDTO) {
        RolModel nuevoRol = rolService.crearRol(rolDTO);
        return ResponseEntity.ok(nuevoRol);
    }

    @Operation(summary = "Obtener un rol por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<RolModel> obtenerRolPorId(@PathVariable Long id) {
        Optional<RolModel> rol = rolService.obtenerRolPorId(id);
        return rol.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener un rol por su nombre")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolModel> obtenerRolPorNombre(@PathVariable String nombre) {
        Optional<RolModel> rol = rolService.obtenerRolPorNombre(nombre);
        return rol.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un rol por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRol(@PathVariable Long id) {
        try {
            rolService.eliminarRol(id);
            return ResponseEntity.ok("Rol eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualiza un rol existente",
            description = "Actualiza el nombre y la descripción de un rol según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<RolModel> actualizarRol(@PathVariable Long id, @RequestBody @Valid RolDTO rolDTO) {
        try {
            RolModel rolActualizado = rolService.actualizarRol(id, rolDTO);

            return ResponseEntity.ok(rolActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}