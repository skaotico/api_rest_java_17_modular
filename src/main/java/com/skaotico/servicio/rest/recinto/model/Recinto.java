package com.skaotico.servicio.rest.recinto.model;

import com.skaotico.servicio.rest.recinto.dto.RecintoCreateDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un Recinto dentro del sistema.
 * Contiene información básica como nombre, ubicación, capacidad de áreas y descripción.
 */
@Entity
@Table(name = "recinto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recinto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String ubicacion;

    private Integer capacidadAreas;

    private String descripcion;

    /**
     * Crea una instancia de Recinto a partir de un DTO.
     *
     * @param dto DTO con los datos del recinto
     * @return Recinto con los campos mapeados desde el DTO
     */
    public static Recinto fromDTO(RecintoCreateDTO dto) {
        return Recinto.builder()
                .nombre(dto.getNombre())
                .ubicacion(dto.getUbicacion())
                .capacidadAreas(dto.getCapacidadAreas())
                .descripcion(dto.getDescripcion())
                .build();
    }
}
