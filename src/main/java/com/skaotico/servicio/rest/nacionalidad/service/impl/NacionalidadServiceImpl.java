package com.skaotico.servicio.rest.nacionalidad.service.impl;

import com.skaotico.servicio.rest.nacionalidad.dto.NacionalidadDto;
import com.skaotico.servicio.rest.nacionalidad.model.NacionalidadModel;
import com.skaotico.servicio.rest.nacionalidad.repository.NacionalidadRepository;
import com.skaotico.servicio.rest.nacionalidad.service.NacionalidadService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NacionalidadServiceImpl implements NacionalidadService {

    private final NacionalidadRepository nacionalidadRepository;

    public NacionalidadServiceImpl(NacionalidadRepository nacionalidadRepository) {
        this.nacionalidadRepository = nacionalidadRepository;
    }


    /**
     * Lista todas las nacionalidades existentes.
     *
     * @return Lista de Nacionalidad DTOs
     */
    @Override
    public List<NacionalidadDto> listarNacionalidades() {
        return nacionalidadRepository.findAll()
                .stream()
                .map(n -> new NacionalidadDto(n.getNombre()))
                .collect(Collectors.toList());
    }

    public List<NacionalidadDto> insertarNacionalidadesMasivas(List<NacionalidadDto> nacionalidades) {
        // Convertir DTOs a entidades
        var entidades = nacionalidades.stream()
                .map(dto -> {
                    var entidad = new NacionalidadModel();
                    entidad.setNombre(dto.getNombre());
                    return entidad;
                })
                .collect(Collectors.toList());

        // Guardar todas las entidades
        var guardadas = nacionalidadRepository.saveAll(entidades);

        // Convertir de vuelta a DTOs
        return guardadas.stream()
                .map(n -> new NacionalidadDto(n.getNombre()))
                .collect(Collectors.toList());
    }
}
