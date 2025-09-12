package com.skaotico.servicio.rest.storage.minio;


import java.io.InputStream;
import java.util.List;

public interface MinioService {

    /**
     * Sube un archivo a un bucket o contenedor.
     *
     * @param bucket      nombre del bucket
     * @param objectPath  ruta o nombre del objeto
     * @param file        contenido del archivo
     * @param size        tamaño del archivo
     * @param contentType tipo MIME del archivo
     * @throws Exception en caso de error
     */
    void uploadFile(String bucket, String objectPath, InputStream file, long size, String contentType) throws Exception;

    /**
     * Obtiene un URL pre-firmado para descargar un archivo.
     *
     * @param bucket        nombre del bucket
     * @param objectPath    ruta o nombre del objeto
     * @param expirySeconds tiempo de validez en segundos
     * @return URL pre-firmado
     * @throws Exception en caso de error
     */
    String getPresignedUrl(String bucket, String objectPath, int expirySeconds) throws Exception;

    /**
     * Lista los archivos dentro de un bucket con un prefijo determinado.
     *
     * @param bucket nombre del bucket
     * @param prefix prefijo de búsqueda
     * @return lista de nombres de objetos
     * @throws Exception en caso de error
     */
    List<String> listFiles(String bucket, String prefix) throws Exception;

    /**
     * Elimina un archivo de un bucket.
     *
     * @param bucket     nombre del bucket
     * @param objectPath ruta o nombre del objeto
     * @throws Exception en caso de error
     */
    void deleteFile(String bucket, String objectPath) throws Exception;

    /**
     * Crea un bucket si no existe.
     *
     * @param bucket nombre del bucket
     * @throws Exception en caso de error
     */
    void createBucketIfNotExists(String bucket) throws Exception;

    public String getFileUrl(String bucket, String objectPath)throws Exception;

    public byte[] getFileBytes(String bucket, String objectPath) throws Exception;
}