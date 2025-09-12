package com.skaotico.servicio.rest.arbol.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.arbol.mapper.ArbolMapper;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import com.skaotico.servicio.rest.arbol.repository.ArbolRepository;
import com.skaotico.servicio.rest.arbol.service.ArbolService;
import com.skaotico.servicio.rest.storage.minio.MinioService;
import com.skaotico.servicio.rest.util.MultipartFileUtil;
import com.skaotico.servicio.rest.util.QRUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio {@link ArbolService} para la gestión de árboles y sus imágenes.
 *
 * <p>Esta clase provee la lógica de negocio para:</p>
 * <ul>
 *     <li>Crear árboles a partir de un DTO y almacenar la información en la base de datos.</li>
 *     <li>Generar códigos QR con la información del árbol y almacenarlos en MinIO.</li>
 *     <li>Subir imágenes a MinIO y devolver la URL pública.</li>
 *     <li>Eliminar árboles por su ID.</li>
 *     <li>Buscar árboles por ID o nombre de especie.</li>
 *     <li>Listar todos los árboles registrados.</li>
 * </ul>
 *
 * <p>Reglas de negocio:</p>
 * <ul>
 *     <li>Al crear un árbol, si el DTO contiene metadata, se transforma en {@link JsonNode} y se guarda en el árbol.</li>
 *     <li>Se genera un código QR de 300x300 px que se sube a MinIO, y su URL se añade a la metadata.</li>
 *     <li>Las imágenes se guardan en el bucket {@link #BUCKET} de MinIO y se les asigna un nombre único con UUID.</li>
 *     <li>El método {@link #eliminar(Long)} devuelve true si se eliminó el árbol, false si no existía.</li>
 *     <li>Los métodos de búsqueda y listado devuelven los objetos de dominio {@link ArbolModel} directamente.</li>
 * </ul>
 *
 * <p>Validaciones y formato de campos:</p>
 * <ul>
 *     <li><b>file</b> (MultipartFile): Obligatorio en {@link #guardarImagen(MultipartFile)}. Debe contener datos; si está vacío lanza {@link IllegalArgumentException}.</li>
 *     <li><b>arbolDto</b> (ArbolCreateDto): Obligatorio en {@link #crear(ArbolCreateDto)}. Puede contener metadata opcional.</li>
 *     <li><b>id</b> (Long): Obligatorio en {@link #eliminar(Long)} y {@link #buscarPorId(Long)}.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * ArbolCreateDto dto = new ArbolCreateDto();
 * dto.setNombre("Roble");
 * dto.setEspecie("Quercus");
 *
 * // Crear árbol y generar QR
 * ArbolModel creado = arbolService.crear(dto);
 *
 * // Subir imagen
 * MultipartFile file = ...; // archivo JPG o PNG
 * ImagenResponse imagen = arbolService.guardarImagen(file);
 *
 * // Buscar por ID
 * Optional<ArbolModel> buscado = arbolService.buscarPorId(creado.getId());
 *
 * // Eliminar árbol
 * boolean eliminado = arbolService.eliminar(creado.getId());
 *
 * // Listar todos los árboles
 * List<ArbolModel> lista = arbolService.listarTodos();
 *
 * // Buscar por especie
 * List<ArbolModel> robles = arbolService.listarPorNombre("Quercus");
 * }</pre>
 */
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
     * Guarda un archivo en MinIO y devuelve información de la imagen.
     *
     * @param file Archivo a subir (obligatorio). Debe contener datos; si está vacío, lanza {@link IllegalArgumentException}.
     * @return {@link ImagenResponse} con el nombre único y la URL pública del archivo.
     * @throws Exception si ocurre un error durante la subida al bucket.
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

    /**
     * Crea un árbol a partir de un DTO y genera un código QR con la información del árbol.
     *
     * @param arbolDto DTO con la información del árbol (obligatorio).
     * @return {@link ArbolModel} creado, incluyendo la metadata actualizada con la URL del QR.
     */
    @Override
    public ArbolModel crear(ArbolCreateDto arbolDto) {
        ArbolModel arbol = arbolMapper.toEntity(arbolDto);

        if (arbolDto.getMetadata() != null) {
            arbol.setMetadata(new ObjectMapper().valueToTree(arbolDto.getMetadata()));
        }

        ArbolModel arbolCreado = arbolRepository.save(arbol);

        Gson gson = new Gson();
        try {
            String json = gson.toJson(arbolCreado);
            byte[] qrBytes = QRUtils.generateQRCodeToBytes(json, 300, 300);
            MultipartFile multipartFile = MultipartFileUtil.fromBytes(qrBytes, "qr.png", "image/png");

            JsonNode metadata = arbol.getMetadata();
            ObjectMapper mapper = new ObjectMapper();
            String nombreQr = metadata.get("imageId").asText() + "_qr";
            minioService.uploadFile(
                    BUCKET,
                    nombreQr,
                    multipartFile.getInputStream(),
                    multipartFile.getSize(),
                    multipartFile.getContentType()
            );

            String url = minioService.getFileUrl(BUCKET, nombreQr);
            ObjectNode metadataNode = (ObjectNode) metadata;
            metadataNode.put("qrUrl", url);
            arbol.setMetadata(metadataNode);
            arbolCreado = arbolRepository.save(arbol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arbolCreado;
    }

    /**
     * Elimina un árbol por su ID.
     *
     * @param id ID del árbol (obligatorio)
     * @return {@code true} si el árbol existía y se eliminó, {@code false} si no existía.
     */
    @Override
    public boolean eliminar(Long id) {
        if (arbolRepository.existsById(id)) {
            arbolRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca un árbol por su ID.
     *
     * @param id ID del árbol (obligatorio)
     * @return {@link Optional} con el {@link ArbolModel} si se encontró, vacío en caso contrario.
     */
    @Override
    public Optional<ArbolModel> buscarPorId(Long id) {
        return arbolRepository.findById(id);
    }

    /**
     * Lista todos los árboles registrados.
     *
     * @return Lista de {@link ArbolModel} (puede estar vacía)
     */
    @Override
    public List<ArbolModel> listarTodos() {
        return arbolRepository.findAll();
    }

    /**
     * Lista árboles filtrando por nombre de especie.
     *
     * @param especie Nombre de la especie a buscar (obligatorio)
     * @return Lista de {@link ArbolModel} que contienen la especie indicada, ignorando mayúsculas/minúsculas
     */
    @Override
    public List<ArbolModel> listarPorNombre(String especie) {
        return arbolRepository.findByEspecieContainingIgnoreCase(especie);
    }
}
