package com.skaotico.servicio.rest.arbol.service;

import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArbolService {
    ImagenResponse guardarImagen(MultipartFile file) throws Exception;


    ArbolModel crear(ArbolCreateDto arbol);

    void eliminar(Long id);

    Optional<ArbolModel> buscarPorId(Long id);

    List<ArbolModel> listarTodos();

    List<ArbolModel> listarPorNombre(String especie);

}
