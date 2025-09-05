package com.skaotico.servicio.rest.rol.service;

import com.skaotico.servicio.rest.rol.dto.RolDTO;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import com.skaotico.servicio.rest.rol.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public RolModel crearRol(RolDTO rolDTO) {
        RolModel rolModel = RolModel.fromDTO(rolDTO);
        return rolRepository.save(rolModel);
    }

    @Override
    public RolModel actualizarRol(Long id, RolDTO rolDtoActualizado) {
        RolModel rolModelActualizado = RolModel.fromDTO((rolDtoActualizado));
        return rolRepository.findById(id)
                .map(rolModel -> {
                    rolModel.setNombre(rolModelActualizado.getNombre());
                    rolModel.setDescripcion(rolModelActualizado.getDescripcion());
                    return rolRepository.save(rolModel);
                })
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }

    @Override
    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<RolModel> obtenerRolPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Optional<RolModel> obtenerRolPorNombre(String nombre) {
        RolUsuarioEnum rolEnum = RolUsuarioEnum.fromString(nombre);

        return rolRepository.findByNombre(rolEnum);
    }

    @Override
    public List<RolModel> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }
}
