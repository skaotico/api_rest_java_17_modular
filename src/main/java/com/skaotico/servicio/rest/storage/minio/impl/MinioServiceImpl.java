package com.skaotico.servicio.rest.storage.minio.impl;

import com.skaotico.servicio.rest.storage.minio.MinioService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    public MinioServiceImpl(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.user}") String user,
            @Value("${minio.pass}") String pass) {

        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(user, pass)
                .build();
    }

    @Override
    public void createBucketIfNotExists(String bucket) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    @Override
    public void uploadFile(String bucket, String objectPath, InputStream file, long size, String contentType) throws Exception {
        createBucketIfNotExists(bucket);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectPath)
                        .stream(file, size, -1)
                        .contentType(contentType)
                        .build()
        );
    }

    @Override
    public String getPresignedUrl(String bucket, String objectPath, int expirySeconds) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(objectPath)
                        .method(Method.GET)
                        .expiry(expirySeconds)
                        .build()
        );
    }

    /**
     * Método de conveniencia que devuelve un URL pre-firmado por defecto con 1 hora de validez.
     *
     * @param bucket     nombre del bucket
     * @param objectPath nombre del archivo en el bucket
     * @return URL pre-firmado de acceso
     * @throws Exception en caso de error
     */
    @Override
    public String getFileUrl(String bucket, String objectPath) throws Exception {
        int maxExpiry = 7 * 24 * 60 * 60;
        return getPresignedUrl(bucket, objectPath, maxExpiry);
    }

    @Override
    public List<String> listFiles(String bucket, String prefix) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucket)
                        .prefix(prefix)
                        .build()
        );

        return StreamSupport.stream(results.spliterator(), false)
                .map(result -> {
                    try {
                        return result.get().objectName();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String bucket, String objectPath) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(objectPath)
                .build());
    }
    /**
     * Obtiene el contenido de un archivo almacenado en MinIO como un arreglo de bytes.
     * <p>
     * Este método se conecta al bucket especificado en MinIO y lee el objeto identificado
     * por {@code objectPath}. Devuelve todo el contenido del archivo como un arreglo de bytes,
     * lo cual es útil para enviar la información directamente al cliente o para procesarla en memoria.
     * </p>
     *
     * @param bucket     el nombre del bucket donde se encuentra el archivo en MinIO
     * @param objectPath la ruta u objeto dentro del bucket que se desea leer
     * @return un arreglo de bytes ({@code byte[]}) con el contenido completo del archivo
     * @throws Exception si ocurre algún error al conectarse a MinIO, si el bucket u objeto
     *                   no existen, o si ocurre algún problema de lectura del InputStream
     *
     * @implNote Este método utiliza try-with-resources para asegurar que el InputStream se cierre
     *           correctamente después de leer los datos, evitando fugas de memoria.
     */
    public byte[] getFileBytes(String bucket, String objectPath) throws Exception {
        try (InputStream is = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectPath)
                        .build())) {
            return is.readAllBytes();
        }
    }

}
