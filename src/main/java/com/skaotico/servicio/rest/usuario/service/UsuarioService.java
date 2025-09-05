package com.skaotico.servicio.rest.usuario.service;

import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;

import java.util.List;

public interface UsuarioService {

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuarioDto Objeto UsuarioCreateDTO con los datos a guardar.
     * @return Usuario guardado.
     */
    Usuario crearUsuario(UsuarioCreateDTO usuarioDto);

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado.
     */
    Usuario obtenerUsuarioPorId(Long id);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     */
    void eliminarUsuario(Long id);

    /**
     * Lista todos los usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    List<Usuario> listarTodosUsuarios();

    /**
     * Modifica un usuario existente.
     *
     * @param id ID del usuario a modificar.
     * @param usuarioDto DTO con los nuevos datos.
     * @return Usuario actualizado.
     */
    //Usuario modificarUsuario(Long id, UsuarioCreateDTO usuarioDto);



    Usuario obtenerUsuarioPorEmail(String email);

}
