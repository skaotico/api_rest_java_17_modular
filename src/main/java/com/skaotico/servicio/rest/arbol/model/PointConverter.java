package com.skaotico.servicio.rest.arbol.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.geometric.PGpoint;
import com.skaotico.servicio.rest.arbol.dto.LatLngDto;

@Converter
public class PointConverter implements AttributeConverter<LatLngDto, PGpoint> {

    @Override
    public PGpoint convertToDatabaseColumn(LatLngDto latLng) {
        if (latLng == null) return null;
        return new PGpoint(latLng.getLat(), latLng.getLng());
    }

    @Override
    public LatLngDto convertToEntityAttribute(PGpoint pgPoint) {
        if (pgPoint == null) return null;
        return new LatLngDto(pgPoint.x, pgPoint.y);
    }
}
