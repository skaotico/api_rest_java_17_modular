package com.skaotico.servicio.rest.rol.mapper;


import org.mapstruct.Mapper;
import com.skaotico.servicio.rest.rol.dto.RolDTO;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.util.BaseMapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RolMapper  extends BaseMapper<RolDTO, RolModel> {

    @Override
    @Mapping(target = "id", ignore = true)
    RolModel toModel(RolDTO dto);
}