package com.skaotico.servicio.rest.usuario.service.impl;

import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import com.skaotico.servicio.rest.storage.minio.MinioService;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.mapper.UsuarioMapper;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.usuario.repository.UsuarioRepository;
import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import org.springframework.security.oauth2.jwt.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio que implementa la lógica de negocio para la gestión de usuarios.
 * <p>
 * Esta clase se encarga de operaciones como crear, obtener, listar y eliminar usuarios,
 * así como gestionar la carga de imágenes de perfil usando MinIO.
 * </p>
 *
 * <h2>Campos principales</h2>
 * <ul>
 *   <li><b>usuarioRepository</b> (obligatorio): Repositorio de acceso a datos de Usuario.</li>
 *   <li><b>passwordEncoder</b> (obligatorio): Codificador de contraseñas para almacenar de forma segura.</li>
 *   <li><b>minioService</b> (obligatorio): Servicio de integración con MinIO para almacenamiento de archivos.</li>
 *   <li><b>usuarioMapper</b> (obligatorio): Mapper para convertir entre DTO y entidad Usuario.</li>
 *   <li><b>bucketUsuarios</b> (obligatorio): Nombre del bucket en MinIO donde se almacenan las imágenes de usuario.</li>
 * </ul>
 *
 * <h2>Reglas de negocio</h2>
 * <ul>
 *   <li>Al crear un usuario, la contraseña se codifica automáticamente usando BCrypt.</li>
 *   <li>Se permite obtener un usuario por su ID o email. Si no existe, se lanza RuntimeException.</li>
 *   <li>Al eliminar un usuario, se valida que exista previamente.</li>
 *   <li>Al subir una imagen de perfil, se genera un nombre único con UUID y se reemplazan espacios en blanco.</li>
 *   <li>Se asegura que el bucket en MinIO exista antes de subir la imagen.</li>
 *   <li>La URL de la imagen se almacena en la propiedad {@code fotoPerfil} del usuario.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * @Autowired
 * private UsuarioService usuarioService;
 *
 * // Crear un nuevo usuario
 * UsuarioCreateDTO dto = new UsuarioCreateDTO();
 * dto.setNombre("Juan Perez");
 * dto.setEmail("juan.perez@email.com");
 * dto.setPassword("123456");
 * Usuario usuarioCreado = usuarioService.crearUsuario(dto);
 *
 * // Obtener usuario por email
 * Usuario usuario = usuarioService.obtenerUsuarioPorEmail("juan.perez@email.com");
 *
 * // Subir imagen de perfil
 * MultipartFile file = ... // archivo desde frontend
 * Jwt jwt = ... // token del usuario autenticado
 * Usuario usuarioConFoto = usuarioService.guardarImagenUsuario(file, jwt);
 *
 * // Listar todos los usuarios
 * List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
 * }</pre>
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Repositorio de acceso a datos de usuarios.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Codificador de contraseñas para almacenar de forma segura.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Servicio para interactuar con MinIO para almacenamiento de archivos.
     */
    private final MinioService minioService;

    /**
     * Mapper para convertir entre DTO y entidad Usuario.
     */
    private final UsuarioMapper usuarioMapper;

    /**
     * Nombre del bucket en MinIO donde se almacenan las imágenes de usuario.
     */
    @Value("${minio.bucket.usuarios}")
    private String bucketUsuarios;

    /**
     * Constructor para inyectar dependencias finales.
     *
     * @param minioService  Servicio de almacenamiento MinIO.
     * @param usuarioMapper Mapper para Usuario.
     */
    public UsuarioServiceImpl(MinioService minioService, UsuarioMapper usuarioMapper) {
        this.minioService = minioService;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuarioDto DTO con los datos del usuario a crear.
     * @return Usuario creado con ID generado.
     * @throws RuntimeException si ocurre un error en la creación.
     */
    public Usuario crearUsuario(UsuarioCreateDTO usuarioDto) {
        try {
            Usuario usuario = usuarioMapper.toModel(usuarioDto);
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario correspondiente al ID.
     * @throws RuntimeException si el usuario no existe.
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
     * @return Lista de usuarios.
     * @throws RuntimeException si ocurre un error al listar.
     */
    public List<Usuario> listarTodosUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Usuario correspondiente al email.
     * @throws RuntimeException si no existe el usuario.
     */
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
    @Override
    public Usuario guardarImagenUsuario(MultipartFile file, Jwt token) {
        System.out.println("Inicio del método guardarImagenUsuario");

        String email = token.getClaimAsString("usuEmail");
        System.out.println("Email obtenido del token: " + email);

        Usuario usuario = this.obtenerUsuarioPorEmail(email);
        if (usuario == null) {
            System.out.println("Usuario no encontrado para el email: " + email);
            throw new RuntimeException("Usuario no encontrado para el email: " + email);
        }
        System.out.println("Usuario encontrado: " + usuario.getRut() + " - " + usuario.getNombre());

        String originalFilename = file.getOriginalFilename();
        System.out.println("Nombre original del archivo: " + originalFilename);

        String nombreArchivo = UUID.randomUUID() + "_" + (originalFilename != null ? originalFilename.replaceAll("\\s+", "_") : "imagen");
        String objectPath = usuario.getRut() + "/" + nombreArchivo;
        System.out.println("Nombre final del archivo: " + nombreArchivo);
        System.out.println("Ruta en bucket: " + objectPath);

        try {
            System.out.println("Verificando existencia del bucket: " + bucketUsuarios);
            minioService.createBucketIfNotExists(bucketUsuarios);

            System.out.println("Subiendo archivo al bucket...");
            try (InputStream is = file.getInputStream()) {
                minioService.uploadFile(bucketUsuarios, objectPath, is, file.getSize(), file.getContentType());
            }

            String url = minioService.getFileUrl(bucketUsuarios, objectPath);
            System.out.println("Archivo subido con éxito. URL obtenida: " + url);

            usuario.setFotoPerfil(objectPath);
            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            System.out.println("Usuario actualizado y guardado correctamente: " + usuarioGuardado.getRut());

            return usuarioGuardado;
        } catch (Exception e) {
            System.out.println("Error al guardar la imagen de perfil: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo guardar la imagen de perfil", e);
        }
    }

}
