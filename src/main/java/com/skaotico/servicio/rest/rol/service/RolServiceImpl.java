package com.skaotico.servicio.rest.rol.service;

import com.skaotico.servicio.rest.rol.dto.RolDTO;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import com.skaotico.servicio.rest.rol.repository.RolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private static final Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public RolModel crearRol(RolDTO rolDTO) {
        logger.info("Creando rol: {}", rolDTO.getNombre());
        RolModel rolModel = RolModel.fromDTO(rolDTO);
        RolModel rolGuardado = rolRepository.save(rolModel);
        logger.info("Rol creado con id: {}", rolGuardado.getId());
        return rolGuardado;
    }

    @Override
    public RolModel actualizarRol(Long id, RolDTO rolDtoActualizado) {
        logger.info("Actualizando rol con id: {}", id);
        RolModel rolModelActualizado = RolModel.fromDTO(rolDtoActualizado);
        return rolRepository.findById(id)
                .map(rolModel -> {
                    rolModel.setNombre(rolModelActualizado.getNombre());
                    rolModel.setDescripcion(rolModelActualizado.getDescripcion());
                    RolModel actualizado = rolRepository.save(rolModel);
                    logger.info("Rol actualizado con id: {}", actualizado.getId());
                    return actualizado;
                })
                .orElseThrow(() -> {
                    logger.error("No se encontró rol con id: {}", id);
                    return new RuntimeException("Rol no encontrado con id: " + id);
                });
    }

    @Override
    public void eliminarRol(Long id) {
        logger.info("Eliminando rol con id: {}", id);
        if (!rolRepository.existsById(id)) {
            logger.error("No se encontró rol para eliminar con id: {}", id);
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
        logger.info("Rol eliminado con id: {}", id);
    }

    @Override
    public Optional<RolModel> obtenerRolPorId(Long id) {
        logger.debug("Buscando rol por id: {}", id);
        return rolRepository.findById(id);
    }

    @Override
    public Optional<RolModel> obtenerRolPorNombre(String nombre) {
        logger.debug("Buscando rol por nombre: {}", nombre);
        RolUsuarioEnum rolEnum = RolUsuarioEnum.fromString(nombre);
        return rolRepository.findByNombre(rolEnum);
    }

    @Override
    public List<RolModel> obtenerTodosLosRoles() {
        logger.debug("Obteniendo todos los roles");
        return rolRepository.findAll();
    }
}
