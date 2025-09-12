package com.skaotico.servicio.rest.nacionalidad.repository;

import com.skaotico.servicio.rest.nacionalidad.model.NacionalidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NacionalidadRepository extends JpaRepository<NacionalidadModel, Long> {

}
