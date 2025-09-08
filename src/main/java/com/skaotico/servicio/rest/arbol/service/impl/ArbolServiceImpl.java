package com.skaotico.servicio.rest.arbol.service.impl;


import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import com.skaotico.servicio.rest.arbol.repository.ArbolRepository;
import com.skaotico.servicio.rest.arbol.service.ArbolService;
import com.skaotico.servicio.rest.storage.minio.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ArbolServiceImpl implements ArbolService {


    private final ArbolRepository arbolRepository;

    private final MinioService minioService;
    private static final String BUCKET = "arbol-images";

    public ArbolServiceImpl(ArbolRepository arbolRepository, MinioService minioService) {
        this.arbolRepository = arbolRepository;
        this.minioService = minioService;
    }

    @Override
    public String guardarImagen(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }


        minioService.createBucketIfNotExists(BUCKET);


        String objectName = file.getOriginalFilename();


        minioService.uploadFile(
                BUCKET,
                objectName,
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
        );


        return objectName;
    }

    @Override
    public ArbolModel crear(ArbolModel arbol) {
        return arbolRepository.save(arbol);
    }

    @Override
    public void eliminar(Long id) {
        arbolRepository.deleteById(id);
    }

    @Override
    public Optional<ArbolModel> buscarPorId(Long id) {
        return arbolRepository.findById(id);
    }

    @Override
    public List<ArbolModel> listarTodos() {
        return arbolRepository.findAll();
    }

    @Override
    public List<ArbolModel> listarPorNombre(String especie) {
        return arbolRepository.findByEspecieContainingIgnoreCase(especie);
    }
}

