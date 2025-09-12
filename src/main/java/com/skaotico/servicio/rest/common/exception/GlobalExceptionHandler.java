package com.skaotico.servicio.rest.common.exception;

import com.skaotico.servicio.rest.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * <h1>GlobalExceptionHandler</h1>
 *
 * <p>
 * Clase encargada de manejar de manera centralizada todas las excepciones
 * que ocurren dentro de los controladores REST de la aplicación. Esta clase
 * permite capturar errores específicos de base de datos, así como errores
 * generales de la aplicación, y devolverlos en un formato estándar definido
 * por {@link com.skaotico.servicio.rest.common.dto.ApiResponse}, incluyendo información adicional útil
 * para el cliente y para debugging.
 * </p>
 *
 * <p>
 * Se utiliza el patrón {@code @RestControllerAdvice} para interceptar
 * excepciones lanzadas desde cualquier módulo o controlador, evitando duplicación
 * de código en cada endpoint.
 * </p>
 *
 * <p><b>Excepciones manejadas:</b></p>
 * <ul>
 *     <li>{@link EmptyResultDataAccessException}: cuando no se encuentra un registro en la base de datos.</li>
 *     <li>{@link DataIntegrityViolationException}: cuando ocurre un error de integridad en la base de datos (FK, unicidad, constraints).</li>
 *     <li>{@link Exception}: captura cualquier otra excepción no prevista para evitar fugas de errores al cliente.</li>
 * </ul>
 *
 * <p><b>Reglas de negocio:</b></p>
 * <ul>
 *     <li>Todos los errores devuelven un objeto {@link ApiResponse} con <code>success=false</code> y <code>data=null</code>.</li>
 *     <li>Se agrega un <code>timestamp</code> indicando el momento del error.</li>
 *     <li>Se agrega un <code>path</code> indicando el endpoint que lanzó la excepción.</li>
 *     <li>Opcionalmente se puede incluir un <code>errorCode</code> para clasificar tipos de errores.</li>
 *     <li>No se deben incluir datos sensibles en el mensaje.</li>
 *     <li>Errores específicos de BD se mapean a códigos HTTP adecuados (404, 409, 500).</li>
 * </ul>
 *
 * <p><b>Formato de la respuesta:</b></p>
 * <pre>{@code
 * {
 *   "success": false,
 *   "data": null,
 *   "message": "Descripción del error",
 *   "timestamp": "2025-09-12T17:50:30.123Z",
 *   "path": "/api/arboles/123",
 *   "errorCode": "NOT_FOUND"
 * }
 * }</pre>
 *
 * <p><b>Ejemplo de uso en un controlador:</b></p>
 * <pre>{@code
 * @DeleteMapping("/arboles/{id}")
 * public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
 *     arbolService.eliminar(id); // si no existe, lanza EmptyResultDataAccessException
 *     ApiResponse<Void> response = new ApiResponse<>(true, null, "Árbol eliminado correctamente");
 *     return ResponseEntity.ok(response);
 * }
 * }</pre>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones cuando no se encuentra un registro en la base de datos.
     *
     * @param ex  La excepción lanzada por Spring Data JPA al intentar acceder o eliminar un registro inexistente.
     * @param request HttpServletRequest para obtener información del endpoint.
     * @return {@link ResponseEntity} con {@link ApiResponse} indicando el error.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmptyResult(EmptyResultDataAccessException ex, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(false, null,
                "Registro no encontrado en la base de datos | Path: " + request.getRequestURI() + " | Timestamp: " + Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Maneja violaciones de integridad de la base de datos, como restricciones de clave foránea o unicidad.
     *
     * @param ex  La excepción lanzada por Spring Data JPA o JDBC al violar constraints de la base de datos.
     * @param request HttpServletRequest para obtener información del endpoint.
     * @return {@link ResponseEntity} con {@link ApiResponse} indicando el error.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(false, null,
                "Error de integridad en la base de datos | Path: " + request.getRequestURI() + " | Timestamp: " + Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Maneja cualquier otra excepción no prevista, asegurando que el cliente siempre reciba un
     * {@link ApiResponse} consistente y evitando fugas de stack trace.
     *
     * @param ex  La excepción no capturada por otros handlers.
     * @param request HttpServletRequest para obtener información del endpoint.
     * @return {@link ResponseEntity} con {@link ApiResponse} indicando un error interno.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(false, null,
                "Error interno del servidor | Path: " + request.getRequestURI() + " | Timestamp: " + Instant.now());
        ex.printStackTrace(); // opcional: para logging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
