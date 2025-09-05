package com.skaotico.servicio.rest.recinto.service;

import com.skaotico.servicio.rest.recinto.dto.RecintoCreateDTO;
import com.skaotico.servicio.rest.recinto.model.Recinto;
import com.skaotico.servicio.rest.recinto.repository.RecintoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecintoService {

    @Autowired
    private RecintoRepository recintoRepository;

    /**
     * Crea un nuevo recinto en la base de datos.
     *
     * @param recinto Objeto Recinto a guardar
     * @return El Recinto guardado con su ID generado
     */
    public Recinto crearRecinto(RecintoCreateDTO recintoDto) {
        Recinto recinto = Recinto.fromDTO(recintoDto);
        return recintoRepository.save(recinto);
    }

    /**
     * Obtiene un Recinto por su identificador.
     *
     * @param id el identificador del Recinto que se desea obtener.
     * @return el Recinto correspondiente al id proporcionado.
     * @throws RuntimeException si no se encuentra ningún Recinto con el id dado.
     */
    public Recinto obtenerRecintoPorId(Long id) {
        try {
            Optional<Recinto> recinto = recintoRepository.findById(id);
            if (recinto.isPresent()) {
                return recinto.get();
            } else {
                throw new RuntimeException("Recinto no encontrado con id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener Recinto: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Elimina un recinto por su ID.
     *
     * @param id ID del recinto
     */
    public void eliminarRecinto(Long id) {
        recintoRepository.deleteById(id);
    }


    /**
     * Obtiene todos los recintos registrados en la base de datos.
     *
     * @return una lista con todos los Recintos.
     */
    public List<Recinto> listarTodosRecintos() {
        try {
            List<Recinto> recintos = recintoRepository.findAll();

            return recintos;
        } catch (Exception e) {
            System.out.println("Error al listar los recintos: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Modifica un recinto existente con los datos proporcionados en el DTO.
     *
     * @param id  ID del recinto a modificar
     * @param dto DTO con los nuevos datos del recinto
     * @return Recinto actualizado
     * @throws RuntimeException si el recinto no existe o ocurre un error al guardar
     */
    public Recinto modificarRecinto(Long id, RecintoCreateDTO dto) {
        try {
            Recinto recintoExistente = recintoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recinto no encontrado con id: " + id));

            if (dto.getNombre() != null) recintoExistente.setNombre(dto.getNombre());
            if (dto.getUbicacion() != null) recintoExistente.setUbicacion(dto.getUbicacion());
            if (dto.getCapacidadAreas() != null) recintoExistente.setCapacidadAreas(dto.getCapacidadAreas());
            if (dto.getDescripcion() != null) recintoExistente.setDescripcion(dto.getDescripcion());

            return recintoRepository.save(recintoExistente);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar recinto", e);
        }
    }

}
