package com.skaotico.servicio.rest.area.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAreaDTO {
    private String nombre;
    private Long recintoId;
    private Double superficieM2;
    private String tipoArea;
}
