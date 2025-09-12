package com.skaotico.servicio.rest.arbol.controller;

import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import com.skaotico.servicio.rest.arbol.service.ArbolService;
import com.skaotico.servicio.rest.common.dto.ApiResponse;
import com.skaotico.servicio.rest.storage.minio.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para la gestión de árboles y sus imágenes.
 *
 * <p>Esta clase expone los endpoints para:</p>
 * <ul>
 *   <li>Crear un árbol</li>
 *   <li>Eliminar un árbol</li>
 *   <li>Buscar árboles por ID o especie</li>
 *   <li>Listar todos los árboles</li>
 *   <li>Subir y descargar imágenes asociadas a los árboles</li>
 * </ul>
 *
 * <p>Todos los endpoints JSON devuelven respuestas estandarizadas usando {@link ApiResponse}:</p>
 * <ul>
 *   <li><b>success</b> (boolean): indica si la operación fue exitosa. Obligatorio.</li>
 *   <li><b>data</b> (T): contenido de la respuesta. Opcional, puede ser null si hay error.</li>
 *   <li><b>message</b> (String): mensaje informativo o de error. Opcional.</li>
 * </ul>
 *
 * <p>Reglas de negocio:</p>
 * <ul>
 *   <li>El service siempre devuelve objetos de dominio o DTOs; el Controller envuelve la respuesta en ApiResponse.</li>
 *   <li>Si success es true, data contendrá el objeto devuelto.</li>
 *   <li>Si success es false, message debe describir el error.</li>
 *   <li>No se deben exponer datos sensibles en data o message.</li>
 * </ul>
 *
 * <p>Ejemplo de uso de API JSON:</p>
 * <pre>{@code
 * // Crear árbol
 * POST /arbol
 * Body: { "nombre": "Roble", "especie": "Quercus" }
 *
 * Response:
 * {
 *   "success": true,
 *   "data": { "id": 1, "nombre": "Roble", "especie": "Quercus" },
 *   "message": "Árbol creado exitosamente"
 * }
 *
 * // Error al buscar árbol
 * GET /arbol/999
 *
 * Response:
 * {
 *   "success": false,
 *   "data": null,
 *   "message": "Árbol no encontrado"
 * }
 * }</pre>
 *
 * <p>Para endpoints de imágenes (subida/descarga) se manejan directamente los binarios
 * y no se envuelven en ApiResponse.</p>
 */
@RestController
@RequestMapping("/arbol")
public class ArbolController {

    private final ArbolService arbolService;
    private final MinioService minioService;

    public ArbolController(ArbolService arbolService, MinioService minioService) {
        this.arbolService = arbolService;
        this.minioService = minioService;
    }

    // ====================== ENDPOINTS ======================

    /**
     * Sube una imagen asociada a un árbol.
     *
     * @param file Archivo de imagen (JPG o PNG). Obligatorio.
     * @return ApiResponse con el objeto ImagenResponse si fue exitosa la subida,
     *         o con mensaje de error en caso contrario.
     */
    @PostMapping(value = "/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImagenResponse>> subirImagen(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, null, "No se recibió ningún archivo"));
        }
        try {
            ImagenResponse imagen = arbolService.guardarImagen(file);
            return ResponseEntity.ok(new ApiResponse<>(true, imagen, "Imagen subida exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, null, "Error al subir la imagen: " + e.getMessage()));
        }
    }

    /**
     * Crea un nuevo árbol.
     *
     * @param arbol DTO con los datos del árbol. Obligatorio.
     * @return ApiResponse con el objeto ArbolModel creado.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ArbolModel>> crear(@RequestBody ArbolCreateDto arbol) {
        ArbolModel creado = arbolService.crear(arbol);
        return ResponseEntity.ok(new ApiResponse<>(true, creado, "Árbol creado exitosamente"));
    }

    /**
     * Elimina un árbol por su ID.
     *
     * @param id ID del árbol a eliminar. Obligatorio.
     * @return ApiResponse vacío si fue exitoso, o con mensaje de error si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        boolean eliminado = arbolService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok(new ApiResponse<>(true, null, "Árbol eliminado exitosamente"));
        } else {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, null, "Árbol no encontrado"));
        }
    }

    /**
     * Busca un árbol por su ID.
     *
     * @param id ID del árbol. Obligatorio.
     * @return ApiResponse con el árbol encontrado o mensaje de error si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArbolModel>> buscarPorId(@PathVariable Long id) {
        return arbolService.buscarPorId(id)
                .map(arbol -> ResponseEntity.ok(new ApiResponse<>(true, arbol, "Árbol encontrado")))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, null, "Árbol no encontrado")));
    }

    /**
     * Lista todos los árboles registrados.
     *
     * @return ApiResponse con la lista de árboles.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArbolModel>>> listarTodos() {
        List<ArbolModel> lista = arbolService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, lista, "Lista de árboles obtenida"));
    }

    /**
     * Lista los árboles filtrando por nombre de especie.
     *
     * @param especie Nombre de la especie. Obligatorio.
     * @return ApiResponse con la lista filtrada.
     */
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<ArbolModel>>> listarPorNombre(@RequestParam String especie) {
        List<ArbolModel> lista = arbolService.listarPorNombre(especie);
        return ResponseEntity.ok(new ApiResponse<>(true, lista, "Lista filtrada por especie"));
    }

    /**
     * Descarga la imagen de un árbol por su nombre de archivo.
     *
     * @param fileName Nombre del archivo de imagen. Obligatorio.
     * @return Imagen en bytes (JPG o PNG) o 404 si no se encuentra.
     */
    @GetMapping("/imagen/{fileName}")
    public ResponseEntity<byte[]> getImagen(@PathVariable String fileName) {
        try {
            byte[] bytes = minioService.getFileBytes("arbol-images", fileName);
            MediaType mediaType = fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                    ? MediaType.IMAGE_JPEG
                    : MediaType.IMAGE_PNG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
