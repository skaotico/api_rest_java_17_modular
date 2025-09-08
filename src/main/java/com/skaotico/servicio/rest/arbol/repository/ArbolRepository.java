package com.skaotico.servicio.rest.arbol.repository;

import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArbolRepository extends JpaRepository<ArbolModel, Long> {
    List<ArbolModel> findByEspecieContainingIgnoreCase(String especie);
}
