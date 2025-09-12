package com.skaotico.servicio.rest.usuario.service;

import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interfaz para la gestión de usuarios.
 * <p>
 * Define las operaciones principales de negocio relacionadas con los usuarios,
 * incluyendo creación, obtención, listado, eliminación y gestión de imágenes de perfil.
 * </p>
 */
public interface UsuarioService {

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuarioDto DTO con los datos del usuario a crear.
     * @return Usuario creado con ID generado.
     * @throws RuntimeException si ocurre un error en la creación.
     */
    Usuario crearUsuario(UsuarioCreateDTO usuarioDto);

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario correspondiente al ID.
     * @throws RuntimeException si el usuario no existe.
     */
    Usuario obtenerUsuarioPorId(Long id);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @throws RuntimeException si el usuario no existe.
     */
    void eliminarUsuario(Long id);

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return Lista de usuarios.
     * @throws RuntimeException si ocurre un error al listar.
     */
    List<Usuario> listarTodosUsuarios();

    /**
     * Obtiene un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Usuario correspondiente al email.
     * @throws RuntimeException si no existe el usuario.
     */
    Usuario obtenerUsuarioPorEmail(String email);

    /**
     * Guarda la imagen de perfil de un usuario autenticado.
     * <p>
     * La imagen se sube a MinIO y se actualiza la URL en el campo {@code fotoPerfil}.
     * </p>
     *
     * @param file  Archivo de imagen a subir.
     * @param token Token JWT del usuario autenticado.
     * @return Usuario actualizado con la URL de la imagen de perfil.
     * @throws RuntimeException si ocurre un error al subir la imagen.
     */
    Usuario guardarImagenUsuario(MultipartFile file, Jwt token);
}
