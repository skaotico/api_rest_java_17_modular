package com.skaotico.servicio.rest.arbol.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.arbol.mapper.ArbolMapper;
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
import java.util.UUID;

@Service
public class ArbolServiceImpl implements ArbolService {


    private final ArbolRepository arbolRepository;
    private final ArbolMapper arbolMapper;

    private final MinioService minioService;
    private static final String BUCKET = "arbol-images";

    public ArbolServiceImpl(ArbolRepository arbolRepository, ArbolMapper arbolMapper, MinioService minioService) {
        this.arbolRepository = arbolRepository;
        this.arbolMapper = arbolMapper;
        this.minioService = minioService;
    }

    /**
     * Guarda un archivo en MinIO y devuelve la información de la imagen en JSON.
     *
     * @param file el archivo a subir
     * @return un objeto con el ID único y la URL de la imagen
     * @throws Exception si ocurre un error durante la subida o si el archivo está vacío
     */
    @Override
    public ImagenResponse guardarImagen(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        minioService.createBucketIfNotExists(BUCKET);

        String objectName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        minioService.uploadFile(
                BUCKET,
                objectName,
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
        );

        String url = minioService.getFileUrl(BUCKET, objectName);

        return new ImagenResponse(objectName, url);
    }

    @Override
    public ArbolModel crear(ArbolCreateDto arbolDto) {
        ArbolModel arbol = arbolMapper.toEntity(arbolDto);
        if (arbolDto.getMetadata() != null) {
            arbol.setMetadata(new ObjectMapper().valueToTree(arbolDto.getMetadata()));
        }
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

