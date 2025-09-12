package com.skaotico.servicio.rest.usuario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.skaotico.servicio.rest.nacionalidad.model.NacionalidadModel;
import com.skaotico.servicio.rest.usuario.type.GeneroEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Entidad que representa la tabla "usuario" en la base de datos.
 * Contiene la información de los usuarios registrados en el sistema,
 * incluyendo su nacionalidad.
 */
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Size(max = 20)
    private String celular;

    @Size(max = 20)
    private String telefonoFijo;

    @Size(max = 15)
    private String rut;

    private String direccion;

    private String ciudad;

    /**
     * Relación con la entidad Nacionalidad.
     * Reemplaza el campo anterior 'pais'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacionalidad_id")
    @JsonBackReference
    private NacionalidadModel nacionalidad;

    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @Column(nullable = false)
    private Boolean activo;

    private LocalDateTime ultimoLogin;

    @Column(name = "metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false, nullable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @Column(name = "foto_perfil")
    private String fotoPerfil;
}
