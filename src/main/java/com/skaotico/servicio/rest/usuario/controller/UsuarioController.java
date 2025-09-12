package com.skaotico.servicio.rest.usuario.controller;

import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestPart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador REST para la gestión de Usuarios.
 *
 * Proporciona endpoints para crear, consultar y listar usuarios.
 */
@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuarioDto DTO con los datos del usuario a crear.
     * @return ResponseEntity con el usuario creado y código HTTP 201.
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioCreateDTO usuarioDto) {
        Usuario usuarioCreado = usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.status(201).body(usuarioCreado);
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return ResponseEntity con la lista de usuarios y código HTTP 200.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene un usuario específico por su ID.
     *
     * @param id ID del usuario a consultar.
     * @return ResponseEntity con el usuario encontrado y código HTTP 200.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Usuario> findOne(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }


/*
    @PostMapping(value = "/imagen", consumes = "multipart/form-data")
    public ResponseEntity<ImagenResponse> subirImagen(@RequestParam("file") MultipartFile file,@AuthenticationPrincipal Jwt jwt) {
        if (file.isEmpty()) {
            new ImagenResponse(null, "No se recibió ningún archivo");
        }
        try {
            Map<String, Object> claims = jwt.getClaims();

            String email = (String) claims.get("usuEmail");

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ImagenResponse(null, "Ocurrió un error al subir la imagen: " + e.getMessage()));
        }
    }

 */

    @PostMapping(value = "/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir imagen de usuario")
    public String subirImagen(@AuthenticationPrincipal Jwt jwt,
                              @RequestPart(name = "file", required = true) MultipartFile file) {
      ;

        usuarioService.guardarImagenUsuario(file,jwt);

        return null;
    }

}
