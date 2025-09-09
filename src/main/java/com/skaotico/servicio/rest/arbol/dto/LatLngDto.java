package com.skaotico.servicio.rest.arbol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLngDto {
    private double lat;
    private double lng;

    public LatLngDto() {}

    public LatLngDto(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
