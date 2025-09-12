package com.skaotico.servicio.rest.arbol.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.geometric.PGpoint;
import com.skaotico.servicio.rest.arbol.dto.LatLngDto;

/**
 * Convertidor JPA para mapear entre {@link LatLngDto} y {@link PGpoint} de PostgreSQL.
 * <p>
 * Este convertidor permite almacenar objetos {@link LatLngDto} como tipo geométrico {@code point} en PostgreSQL
 * y recuperarlos automáticamente como DTO en la entidad JPA.
 * </p>
 *
 * <h2>Uso</h2>
 * <p>
 * Se puede aplicar directamente en un campo de entidad con la anotación {@code @Convert(converter = PointConverter.class)}.
 * </p>
 *
 * <h2>Ejemplo</h2>
 * <pre>{@code
 * @Entity
 * public class ArbolModel {
 *
 *     @Convert(converter = PointConverter.class)
 *     private LatLngDto gpsPoint;
 * }
 *
 * // Persistir un árbol
 * ArbolModel arbol = new ArbolModel();
 * arbol.setGpsPoint(new LatLngDto(40.4168, -3.7038));
 * arbolRepository.save(arbol);
 *
 * // Recuperar
 * ArbolModel encontrado = arbolRepository.findById(1L).orElseThrow();
 * LatLngDto punto = encontrado.getGpsPoint(); // LatLngDto{lat=40.4168, lng=-3.7038}
 * }</pre>
 */
@Converter
public class PointConverter implements AttributeConverter<LatLngDto, PGpoint> {

    /**
     * Convierte un {@link LatLngDto} en {@link PGpoint} para almacenamiento en la base de datos.
     *
     * @param latLng Punto geográfico DTO.
     * @return PGpoint correspondiente, o null si {@code latLng} es null.
     */
    @Override
    public PGpoint convertToDatabaseColumn(LatLngDto latLng) {
        if (latLng == null) return null;
        return new PGpoint(latLng.getLat(), latLng.getLng());
    }

    /**
     * Convierte un {@link PGpoint} de la base de datos en un {@link LatLngDto}.
     *
     * @param pgPoint Punto geográfico de PostgreSQL.
     * @return LatLngDto correspondiente, o null si {@code pgPoint} es null.
     */
    @Override
    public LatLngDto convertToEntityAttribute(PGpoint pgPoint) {
        if (pgPoint == null) return null;
        return new LatLngDto(pgPoint.x, pgPoint.y);
    }
}
