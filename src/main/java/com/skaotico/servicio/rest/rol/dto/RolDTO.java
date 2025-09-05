package com.skaotico.servicio.rest.rol.dto;

import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir información de los roles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDTO {


    /**
     * Nombre del rol.
     * No puede ser nulo.
     */
    @NotNull(message = "El nombre del rol es obligatorio")
    private RolUsuarioEnum nombre;

    /**
     * Descripción del rol.
     * Máximo 255 caracteres.
     */
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;
}
