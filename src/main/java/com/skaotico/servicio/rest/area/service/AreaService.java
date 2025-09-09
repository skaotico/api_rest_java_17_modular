package com.skaotico.servicio.rest.area.service;

import com.skaotico.servicio.rest.area.dto.AreaDTO;
import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;

import java.util.List;

public interface AreaService {

    AreaDTO createArea(CreateAreaDTO createAreaDTO);

    AreaDTO getAreaById(Long id);

    List<AreaDTO> getAllAreas();

    AreaDTO updateArea(Long id, AreaDTO areaDTO);

    void deleteArea(Long id);
}
