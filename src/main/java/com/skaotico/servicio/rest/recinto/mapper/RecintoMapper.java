package com.skaotico.servicio.rest.recinto.mapper;


import com.skaotico.servicio.rest.recinto.dto.RecintoCreateDTO;
import com.skaotico.servicio.rest.recinto.model.Recinto;
import com.skaotico.servicio.rest.rol.dto.RolDTO;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.util.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RecintoMapper extends BaseMapper<RecintoCreateDTO, Recinto> {

    @Override
    @Mapping(target = "id", ignore = true)
    Recinto toModel(RecintoCreateDTO dto);
}