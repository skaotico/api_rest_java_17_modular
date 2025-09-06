package com.skaotico.servicio.rest.arbol.service.impl;


import com.skaotico.servicio.rest.arbol.service.ArbolService;
import com.skaotico.servicio.rest.storage.minio.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ArbolServiceImpl implements ArbolService {


    private final MinioService minioService;
    private static final String BUCKET = "arbol-images";

    public ArbolServiceImpl(MinioService minioService) {
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
}

