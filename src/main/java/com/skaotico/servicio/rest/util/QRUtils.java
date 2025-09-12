package com.skaotico.servicio.rest.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileSystems;

public class QRUtils {

    /**
     * Genera un archivo PNG con un QR a partir de un JSON (String).
     *
     * @param jsonData  Cadena con el JSON
     * @param filePath  Ruta donde se guardará el QR (ej: "qr.png")
     * @param width     Ancho del QR
     * @param height    Alto del QR
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeToFile(String jsonData, String filePath, int width, int height)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(jsonData, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * Devuelve un arreglo de bytes con la imagen PNG del QR generado
     * (útil para enviar como respuesta HTTP en un servicio REST).
     *
     * @param jsonData  Cadena con el JSON
     * @param width     Ancho del QR
     * @param height    Alto del QR
     * @return          Array de bytes PNG
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] generateQRCodeToBytes(String jsonData, int width, int height)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(jsonData, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
