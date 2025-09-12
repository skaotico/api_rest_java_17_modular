package com.skaotico.servicio.rest.nacionalidad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NacionalidadDto {

    @NotBlank(message = "El nombre de la nacionalidad no puede estar vacío")
    private String nombre;
}