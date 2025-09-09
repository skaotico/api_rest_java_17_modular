package com.skaotico.servicio.rest.area.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "recinto_id", nullable = false)
    private Long recintoId;

    @Column(name = "superficie_m2")
    private Double superficieM2;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_area")
    private TipoAreaEnum tipoArea;


}
