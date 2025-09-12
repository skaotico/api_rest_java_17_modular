package com.skaotico.servicio.rest.area.service;

import com.skaotico.servicio.rest.area.dto.Area;
import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;

import java.util.List;

public interface AreaService {

    Area createArea(CreateAreaDTO createAreaDTO);

    Area getAreaById(Long id);

    List<Area> getAllAreas();

    Area updateArea(Long id, Area area);

    void deleteArea(Long id);
}
