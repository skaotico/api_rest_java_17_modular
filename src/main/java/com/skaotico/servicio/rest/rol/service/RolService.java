package com.skaotico.servicio.rest.rol.service;

import com.skaotico.servicio.rest.rol.dto.RolDTO;
import com.skaotico.servicio.rest.rol.model.RolModel;

import java.util.List;
import java.util.Optional;

public interface RolService {

    RolModel crearRol(RolDTO rolDTO);

    RolModel actualizarRol(Long id, RolDTO rolDTO);

    void eliminarRol(Long id);

    Optional<RolModel> obtenerRolPorId(Long id);

    Optional<RolModel> obtenerRolPorNombre(String nombre);

    List<RolModel> obtenerTodosLosRoles();
}
