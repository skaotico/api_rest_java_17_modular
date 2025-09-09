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

@Mapper(componentModel = "spring")
public interface ArbolMapper {

    @Mapping(source = "areaId", target = "area", qualifiedByName = "mapAreaIdToArea")
    @Mapping(source = "gpsPoint", target = "gpsPoint", qualifiedByName = "mapLatLngToString")
    @Mapping(source = "metadata", target = "metadata") // <-- explícito para MapStruct
    @Mapping(target = "id", ignore = true)
    ArbolModel toEntity(ArbolCreateDto dto);

    @Named("mapAreaIdToArea")
    default Area mapAreaIdToArea(Long areaId) {
        if (areaId == null) return null;
        Area area = new Area();
        area.setId(areaId);
        return area;
    }

    @Named("mapLatLngToString")
    default String mapLatLngToString(LatLngDto latLng) {
        if (latLng == null) return null;
        return "(" + latLng.getLat() + "," + latLng.getLng() + ")";
    }

    // <-- Este método indica a MapStruct cómo convertir Map a JsonNode
    default JsonNode map(Map<String, Object> value) {
        if (value == null) return null;
        return new ObjectMapper().valueToTree(value);
    }
}
