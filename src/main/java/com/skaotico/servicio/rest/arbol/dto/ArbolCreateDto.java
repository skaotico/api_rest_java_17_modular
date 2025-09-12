package com.skaotico.servicio.rest.arbol.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * DTO para la creación de un árbol en el sistema.
 * <p>
 * Esta clase representa los datos necesarios para crear un árbol,
 * incluyendo especie, ubicación geográfica y metadatos opcionales.
 * </p>
 *
 * <h2>Campos</h2>
 * <ul>
 *   <li><b>especie</b> (obligatorio): Nombre de la especie del árbol. No puede ser null.</li>
 *   <li><b>areaId</b> (obligatorio): Identificador del área o parcela donde se encuentra el árbol.</li>
 *   <li><b>gpsPoint</b> (obligatorio): Coordenadas geográficas del árbol, representadas por {@link LatLngDto}.</li>
 *   <li><b>metadata</b> (opcional): Mapa con información adicional sobre el árbol. Puede incluir datos como estado, edad, altura, etc.</li>
 * </ul>
 *
 * <h2>Validaciones</h2>
 * <ul>
 *   <li>Todos los campos anotados con {@code @NotNull} deben proporcionarse al momento de la creación.</li>
 *   <li>El campo {@code metadata} es opcional y puede ser null o vacío.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * ArbolCreateDto arbolDto = new ArbolCreateDto();
 * arbolDto.setEspecie("Quercus robur");
 * arbolDto.setAreaId(123L);
 * arbolDto.setGpsPoint(new LatLngDto(40.4168, -3.7038));
 * Map<String, Object> meta = new HashMap<>();
 * meta.put("edad", 10);
 * meta.put("altura", 5.2);
 * arbolDto.setMetadata(meta);
 * }</pre>
 */
@Getter
@Setter
@ToString
public class ArbolCreateDto {

    /**
     * Nombre de la especie del árbol.
     * <p>No puede ser null.</p>
     */
    @NotNull
    private String especie;

    /**
     * Identificador del área o parcela donde se encuentra el árbol.
     * <p>No puede ser null.</p>
     */
    @NotNull
    private Long areaId;

    /**
     * Coordenadas geográficas del árbol.
     * <p>No puede ser null.</p>
     */
    @NotNull
    private LatLngDto gpsPoint;

    /**
     * Información adicional del árbol.
     * <p>Opcional. Puede contener cualquier dato adicional relevante.</p>
     */
    private Map<String, Object> metadata;
}
