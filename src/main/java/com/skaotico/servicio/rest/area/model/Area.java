package com.skaotico.servicio.rest.area.model;

import com.skaotico.servicio.rest.recinto.model.Recinto;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Representa un Área dentro de un Recinto.
 * Contiene información como nombre, superficie y tipo de área.
 */
@Entity
@Table(name = "area")
public class Area {

    /** Identificador único del área */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del área */
    @Column(nullable = false)
    private String nombre;

    /** Superficie en metros cuadrados */
    @Column(name = "superficie_m2", precision = 8, scale = 2)
    private BigDecimal superficieM2;

    /** Tipo de área */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_area")
    private TipoAreaEnum tipoArea;

    /** Recinto al que pertenece el área */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recinto_id", nullable = false)
    private Recinto recinto;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSuperficieM2() {
        return superficieM2;
    }

    public void setSuperficieM2(BigDecimal superficieM2) {
        this.superficieM2 = superficieM2;
    }

    public TipoAreaEnum getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(TipoAreaEnum tipoArea) {
        this.tipoArea = tipoArea;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }
}
