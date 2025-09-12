package com.skaotico.servicio.rest.nacionalidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa una nacionalidad dentro del sistema.
 * <p>
 * Esta clase mapea la tabla {@code nacionalidad} en la base de datos.
 * Cada nacionalidad puede estar asociada a múltiples usuarios.
 * </p>
 */
@Entity
@Table(name = "nacionalidad")
public class NacionalidadModel {

    /**
     * Identificador único de la nacionalidad.
     * Se genera automáticamente con estrategia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre o gentilicio de la nacionalidad.
     * Debe ser único y no nulo.
     */
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    /**
     * Lista de usuarios asociados a esta nacionalidad.
     * Relación uno a muchos con la entidad {@link Usuario}.
     * Esta relación es opcional y se carga de forma perezosa (lazy).
     */
    @OneToMany(mappedBy = "nacionalidad", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;




    /** @return Identificador de la nacionalidad */
    public Integer getId() {
        return id;
    }

    /** @param id Identificador de la nacionalidad */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return Nombre o gentilicio de la nacionalidad */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nombre o gentilicio de la nacionalidad */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Lista de usuarios asociados a esta nacionalidad */
    @JsonBackReference
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /** @param usuarios Lista de usuarios asociados a esta nacionalidad */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
