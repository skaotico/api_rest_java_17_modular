package com.skaotico.servicio.rest.rol.model;

import com.skaotico.servicio.rest.rol.dto.RolDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla "rol" en la base de datos.
 * Contiene la información de los roles disponibles en el sistema.
 */
@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolModel {

    /**
     * Identificador único del rol.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol.
     * Se mapea al tipo ENUM definido en PostgreSQL: rol_usuario_enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, unique = true)
    private RolUsuarioEnum nombre;

    /**
     * Descripción opcional del rol.
     */
    @Column(name = "descripcion")
    private String descripcion;


    /**
     * Convierte un RolDTO en RolModel.
     */
    public static RolModel fromDTO(RolDTO dto) {
        if (dto == null) {
            return null;
        }

        return RolModel.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();
    }
}
