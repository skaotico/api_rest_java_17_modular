package com.skaotico.servicio.rest.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Utilidad para crear un {@link MultipartFile} a partir de un arreglo de bytes.
 * <p>
 * Esto permite generar archivos en memoria (por ejemplo, códigos QR)
 * y pasarlos a servicios que aceptan {@link MultipartFile} sin necesidad de archivos físicos.
 * </p>
 */
public class MultipartFileUtil {

    /**
     * Crea un {@link MultipartFile} a partir de un arreglo de bytes.
     *
     * @param bytes       El contenido del archivo en bytes.
     * @param filename    El nombre del archivo.
     * @param contentType El tipo MIME del archivo (por ejemplo, "image/png").
     * @return Un {@link MultipartFile} que representa el archivo en memoria.
     */
    public static MultipartFile fromBytes(byte[] bytes, String filename, String contentType) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return bytes == null || bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), bytes);
            }
        };
    }
}
