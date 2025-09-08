package com.skaotico.servicio.rest.arbol.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convierte entre un {@link Map} y su representación JSON para persistencia en la base de datos.
 * <p>
 * Este converter se puede aplicar a columnas de tipo JSON/JSONB en la base de datos.
 * </p>
 */
@Converter(autoApply = false)
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convierte un {@link Map<String, Object>} a su representación JSON en {@link String}.
     *
     * @param attribute el mapa que se quiere persistir en la base de datos
     * @return la cadena JSON correspondiente, o null si el atributo es null
     * @throws IllegalArgumentException si ocurre un error durante la serialización
     */
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializando metadata a JSON", e);
        }
    }

    /**
     * Convierte un {@link String} JSON desde la base de datos a un {@link Map<String, Object>}.
     *
     * @param dbData la cadena JSON almacenada en la base de datos
     * @return el mapa correspondiente, o un {@link HashMap} vacío si dbData es null
     * @throws IllegalArgumentException si ocurre un error durante la deserialización
     */
    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializando metadata desde JSON", e);
        }
    }
}
