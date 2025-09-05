package com.skaotico.servicio.rest.usuario.model;

import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa la tabla "usuario" en la base de datos.
 * Contiene la información de los usuarios registrados en el sistema.
 */
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /**
     * Identificador único del usuario.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    /**
     * Apellido del usuario.
     */
    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    /**
     * Correo electrónico único del usuario.
     */
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Hash de la contraseña del usuario.
     * No se almacena la contraseña en texto plano.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Fecha de creación del usuario.
     * Se asigna automáticamente al insertar un registro.
     */
    @CreationTimestamp
    @Column(name = "creado_en", updatable = false, nullable = false)
    private LocalDateTime creadoEn;

    /**
     * Fecha de última actualización del usuario.
     * Se asigna automáticamente al actualizar el registro.
     */
    @UpdateTimestamp
    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;
}
