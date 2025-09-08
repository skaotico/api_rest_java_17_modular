package com.skaotico.servicio.rest.arbol.controller;

import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import com.skaotico.servicio.rest.arbol.service.ArbolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para la gestión de imágenes relacionadas con "Arbol".
 */
@RestController
@RequestMapping("/arbol")
public class ArbolController {

    private final ArbolService arbolService;

    public ArbolController(ArbolService arbolService) {
        this.arbolService = arbolService;
    }

    /**
     * Sube una imagen relacionada con un árbol.
     *
     * @param file archivo de imagen enviado por el cliente
     * @return mensaje con el estado de la operación
     */
    @Operation(
            summary = "Subir una imagen de árbol",
            description = "Permite subir un archivo de imagen al almacenamiento configurado (ej: MinIO).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Imagen subida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "No se recibió ningún archivo"),
                    @ApiResponse(responseCode = "500", description = "Error al procesar la imagen")
            }
    )
    @PostMapping(value = "/imagen", consumes = "multipart/form-data")
    public ResponseEntity<String> subirImagen(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No se recibió ningún archivo");
        }
        try {
            String objectName = arbolService.guardarImagen(file);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Archivo guardado: " + objectName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ArbolModel> crear(@RequestBody ArbolModel arbol) {
        return ResponseEntity.ok(arbolService.crear(arbol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        arbolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbolModel> buscarPorId(@PathVariable Long id) {
        return arbolService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ArbolModel>> listarTodos() {
        return ResponseEntity.ok(arbolService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArbolModel>> listarPorNombre(@RequestParam String especie) {
        return ResponseEntity.ok(arbolService.listarPorNombre(especie));
    }
}
