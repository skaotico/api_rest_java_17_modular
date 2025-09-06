package com.skaotico.servicio.rest.usuario.mapper;

import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.util.BaseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper implements BaseMapper<UsuarioCreateDTO, Usuario> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // se asigna en @AfterMapping
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    public abstract Usuario toModel(UsuarioCreateDTO dto);

    @AfterMapping
    protected void encodePassword(UsuarioCreateDTO dto, @MappingTarget Usuario usuario) {
        if (dto.getPassword() != null) {
            usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
    }
}