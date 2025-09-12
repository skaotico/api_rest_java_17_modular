package com.skaotico.servicio.rest.arbol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa un punto geográfico mediante coordenadas de latitud y longitud.
 * <p>
 * Usado para identificar la ubicación de un árbol o cualquier entidad geográfica.
 * </p>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * LatLngDto punto = new LatLngDto(40.4168, -3.7038);
 * System.out.println("Latitud: " + punto.getLat());
 * System.out.println("Longitud: " + punto.getLng());
 * }</pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LatLngDto {

    /**
     * Latitud del punto geográfico.
     */
    private double lat;

    /**
     * Longitud del punto geográfico.
     */
    private double lng;
}
