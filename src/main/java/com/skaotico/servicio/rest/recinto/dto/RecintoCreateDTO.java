package com.skaotico.servicio.rest.recinto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecintoCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 200, message = "La ubicación no puede superar 200 caracteres")
    private String ubicacion;

    @PositiveOrZero(message = "La capacidad debe ser 0 o mayor")
    private Integer capacidadAreas;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;
}
