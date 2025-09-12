package com.skaotico.servicio.rest.arbol.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.LatLngDto;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import com.skaotico.servicio.rest.area.model.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Map;

/**
 * Mapper para convertir entre {@link ArbolCreateDto} y {@link ArbolModel}.
 * <p>
 * Utiliza MapStruct para realizar las conversiones automáticas entre DTO y entidad,
 * incluyendo transformaciones personalizadas para campos específicos.
 * </p>
 *
 * <h2>Reglas de mapeo</h2>
 * <ul>
 *   <li>{@code areaId} se convierte en un objeto {@link Area} con solo el ID seteado.</li>
 *   <li>{@code gpsPoint} de tipo {@link LatLngDto} se convierte en un {@link String} con formato "(lat,lng)".</li>
 *   <li>{@code metadata} de tipo {@code Map<String,Object>} se convierte en un {@link JsonNode} usando Jackson.</li>
 *   <li>El campo {@code id} de {@link ArbolModel} se ignora en la creación de la entidad.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * @Autowired
 * private ArbolMapper arbolMapper;
 *
 * ArbolCreateDto dto = new ArbolCreateDto();
 * dto.setEspecie("Quercus robur");
 * dto.setAreaId(123L);
 * dto.setGpsPoint(new LatLngDto(40.4168, -3.7038));
 * Map<String,Object> meta = Map.of("edad", 10, "altura", 5.2);
 * dto.setMetadata(meta);
 *
 * ArbolModel entity = arbolMapper.toEntity(dto);
 * System.out.println(entity.getArea().getId());        // 123
 * System.out.println(entity.getGpsPoint());            // (40.4168,-3.7038)
 * System.out.println(entity.getMetadata().toString()); // {"edad":10,"altura":5.2}
 * }</pre>
 */
@Mapper(componentModel = "spring")
public interface ArbolMapper {

    /**
     * Convierte un {@link ArbolCreateDto} en {@link ArbolModel}.
     *
     * @param dto DTO de creación de árbol.
     * @return Entidad {@link ArbolModel} con campos mapeados.
     */
    @Mapping(source = "areaId", target = "area", qualifiedByName = "mapAreaIdToArea")
    @Mapping(source = "gpsPoint", target = "gpsPoint", qualifiedByName = "mapLatLngToString")
    @Mapping(source = "metadata", target = "metadata") // explícito para MapStruct
    @Mapping(target = "id", ignore = true)
    ArbolModel toEntity(ArbolCreateDto dto);

    /**
     * Convierte un {@link Long} que representa el ID de un área en un objeto {@link Area}.
     *
     * @param areaId ID del área.
     * @return Objeto {@link Area} con el ID seteado, o null si {@code areaId} es null.
     */
    @Named("mapAreaIdToArea")
    default Area mapAreaIdToArea(Long areaId) {
        if (areaId == null) return null;
        Area area = new Area();
        area.setId(areaId);
        return area;
    }

    /**
     * Convierte un {@link LatLngDto} en un {@link String} con formato "(lat,lng)".
     *
     * @param latLng Punto geográfico.
     * @return String con formato "(lat,lng)" o null si {@code latLng} es null.
     */
    @Named("mapLatLngToString")
    default String mapLatLngToString(LatLngDto latLng) {
        if (latLng == null) return null;
        return "(" + latLng.getLat() + "," + latLng.getLng() + ")";
    }

    /**
     * Convierte un {@link Map} en {@link JsonNode} usando Jackson.
     *
     * @param value Mapa de datos a convertir.
     * @return JsonNode correspondiente, o null si {@code value} es null.
     */
    default JsonNode map(Map<String, Object> value) {
        if (value == null) return null;
        return new ObjectMapper().valueToTree(value);
    }
}
