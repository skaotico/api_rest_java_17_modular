package com.skaotico.servicio.rest.recinto.repository;

import com.skaotico.servicio.rest.recinto.model.Recinto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Long> {

}
