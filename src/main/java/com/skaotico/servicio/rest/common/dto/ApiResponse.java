package com.skaotico.servicio.rest.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase genérica para estandarizar las respuestas de la API REST.
 *
 * <p>Esta clase encapsula la información de la respuesta de cualquier endpoint,
 * permitiendo un formato consistente para éxito, datos y mensajes de error o información adicional.</p>
 *
 * <p>Campos:</p>
 * <ul>
 *   <li><b>success</b> (boolean) - Indica si la operación fue exitosa. Obligatorio.</li>
 *   <li><b>data</b> (T) - Contenido de la respuesta. Puede ser un objeto simple, lista u otro DTO. Opcional (puede ser null si hubo error).</li>
 *   <li><b>message</b> (String) - Mensaje descriptivo, de error o informativo. Opcional, puede estar vacío si no es necesario.</li>
 * </ul>
 *
 * <p>Reglas de negocio:</p>
 * <ul>
 *   <li>Si <code>success</code> es true, <code>data</code> normalmente contiene el objeto devuelto.</li>
 *   <li>Si <code>success</code> es false, <code>message</code> debe contener la descripción del error o motivo de fallo.</li>
 *   <li>No se deben incluir datos sensibles en <code>data</code> o <code>message</code>.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * // Respuesta exitosa con datos
 * ApiResponse<ArbolDto> responseOk = new ApiResponse<>(true, arbolDto, "Árbol creado correctamente");
 *
 * // Respuesta con error
 * ApiResponse<ArbolDto> responseError = new ApiResponse<>(false, null, "No se encontró el árbol con el ID proporcionado");
 * }</pre>
 *
 * @param <T> Tipo de datos que se enviarán en el campo 'data'
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
}
