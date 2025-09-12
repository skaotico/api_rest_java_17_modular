package com.skaotico.servicio.rest.usuario.mapper;


import com.skaotico.servicio.rest.nacionalidad.model.NacionalidadModel;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;

import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.util.BaseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper implements BaseMapper<UsuarioCreateDTO, Usuario> {

    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "nacionalidad", source = "nacionalidadId")
    @Mapping(target = "ultimoLogin", ignore = true)
    public abstract Usuario toModel(UsuarioCreateDTO dto);

    @AfterMapping
    protected void encodePassword(UsuarioCreateDTO dto, @MappingTarget Usuario usuario) {
        if (dto.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }


    @Mapping(target = "nacionalidadId", source = "nacionalidad.id")
    public abstract UsuarioCreateDTO toDto(Usuario model);


    protected NacionalidadModel map(Integer id) {
        if (id == null) return null;
        NacionalidadModel n = new NacionalidadModel();
        n.setId(id);
        return n;
    }
}
