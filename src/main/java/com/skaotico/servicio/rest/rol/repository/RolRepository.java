package com.skaotico.servicio.rest.rol.repository;

import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolModel, Long> {
    Optional<RolModel>  findByNombre(RolUsuarioEnum nombre);
}
