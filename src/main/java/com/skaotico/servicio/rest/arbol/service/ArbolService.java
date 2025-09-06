package com.skaotico.servicio.rest.arbol.service;

import org.springframework.web.multipart.MultipartFile;

public interface ArbolService {
    String guardarImagen(MultipartFile file) throws Exception;
}
