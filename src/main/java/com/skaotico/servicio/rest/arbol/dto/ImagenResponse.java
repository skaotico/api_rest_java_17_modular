package com.skaotico.servicio.rest.arbol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO que representa la respuesta de una imagen.
 * <p>
 * Contiene el identificador único de la imagen y su URL accesible públicamente.
 * </p>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * ImagenResponse imagen = new ImagenResponse("12345", "https://minio.example.com/usuarios/imagen.jpg");
 * System.out.println(imagen.getId());  // 12345
 * System.out.println(imagen.getUrl()); // https://minio.example.com/usuarios/imagen.jpg
 * }</pre>
 */
@Getter
@AllArgsConstructor
@ToString
public class ImagenResponse {

    /**
     * Identificador único de la imagen.
     */
    private final String id;

    /**
     * URL pública donde se puede acceder a la imagen.
     */
    private final String url;
}
