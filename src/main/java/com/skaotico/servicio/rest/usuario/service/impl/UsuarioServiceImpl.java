package com.skaotico.servicio.rest.usuario.service.impl;

import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.usuario.repository.UsuarioRepository;
import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuarioDto Objeto UsuarioCreateDTO con los datos a guardar.
     * @return El Usuario guardado con su ID generado.
     */
    public Usuario crearUsuario(UsuarioCreateDTO usuarioDto) {
        try {
            /*
            // Validar que el email no esté registrado
            if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
                throw new RuntimeException("El email ya está registrado: " + usuarioDto.getEmail());
            }
*/
            Usuario usuario = new Usuario();
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setPasswordHash(passwordEncoder.encode(usuarioDto.getPassword()));


            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene un Usuario por su identificador.
     *
     * @param id ID del usuario a obtener.
     * @return El Usuario correspondiente al ID proporcionado.
     * @throws RuntimeException si no se encuentra ningún Usuario con el ID dado.
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            if (usuario.isPresent()) {
                return usuario.get();
            } else {
                throw new RuntimeException("Usuario no encontrado con id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @throws RuntimeException si el usuario no existe.
     */
    public void eliminarUsuario(Long id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new RuntimeException("Usuario no encontrado con id: " + id);
            }
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return Lista con todos los Usuarios.
     */
    public List<Usuario> listarTodosUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            throw e;
        }
    }



    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        try {
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        } catch (Exception e) {
            System.out.println("Error al obtener usuario por email: " + e.getMessage());
            throw e;
        }
    }
}
