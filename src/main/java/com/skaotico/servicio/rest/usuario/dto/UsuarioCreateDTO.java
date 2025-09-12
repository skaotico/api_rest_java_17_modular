package com.skaotico.servicio.rest.usuario.dto;

import com.skaotico.servicio.rest.usuario.type.GeneroEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Map;

/**
 * DTO (Data Transfer Object) utilizado para la creación y actualización de usuarios.
 *
 * <p>Incluye validaciones de campos obligatorios, formatos de datos y restricciones de longitud.
 * Este DTO se utiliza tanto en la creación de un nuevo usuario como en la actualización
 * de un usuario existente.</p>
 *
 * <p>Algunos campos son opcionales y otros obligatorios según las reglas de negocio.</p>
 *
 * <p>Validaciones principales:
 * <ul>
 *   <li>Campos {@link #nombre}, {@link #apellido}, {@link #email} y {@link #password} son obligatorios.</li>
 *   <li>{@link #email} debe tener un formato válido.</li>
 *   <li>{@link #password} debe contener al menos una mayúscula, un número y un carácter especial, con longitud entre 6 y 100 caracteres.</li>
 *   <li>{@link #nombre} y {@link #apellido} solo pueden contener letras y espacios, con un máximo de 50 caracteres.</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * <pre>{@code
 * UsuarioCreateDTO usuario = UsuarioCreateDTO.builder()
 *     .nombre("Juan")
 *     .apellido("Pérez")
 *     .email("juan.perez@email.com")
 *     .password("P@ssw0rd")
 *     .celular("123456789")
 *     .activo(true)
 *     .build();
 * }</pre>
 * </p>
 *
 * @author
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateDTO {

    /**
     * Nombre del usuario.
     * Obligatorio. Solo permite letras y espacios. Máximo 50 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    /**
     * Apellido del usuario.
     * Obligatorio. Solo permite letras y espacios. Máximo 50 caracteres.
     */
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "El apellido solo puede contener letras y espacios")
    private String apellido;

    /**
     * Correo electrónico del usuario.
     * Obligatorio. Debe ser un email válido. Máximo 100 caracteres.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede superar 100 caracteres")
    private String email;

    /**
     * Contraseña del usuario.
     * Obligatorio. Debe tener entre 6 y 100 caracteres, contener al menos
     * una letra mayúscula, un número y un carácter especial.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])[A-Za-z\\d\\S]+$",
            message = "La contraseña debe contener al menos una mayúscula, un número y un carácter especial"
    )
    private String password;

    /**
     * Número de celular del usuario.
     * Opcional. Máximo 20 caracteres.
     */
    @Size(max = 20)
    private String celular;

    /**
     * Número de teléfono fijo del usuario.
     * Opcional. Máximo 20 caracteres.
     */
    @Size(max = 20)
    private String telefonoFijo;

    /**
     * RUT del usuario.
     * Opcional. Máximo 15 caracteres.
     */
    @Size(max = 15)
    private String rut;

    /**
     * Dirección del usuario.
     * Opcional. Máximo 100 caracteres.
     */
    @Size(max = 100)
    private String direccion;

    /**
     * Ciudad del usuario.
     * Opcional. Máximo 50 caracteres.
     */
    @Size(max = 50)
    private String ciudad;

    /**
     * Identificador de la nacionalidad del usuario.
     * Opcional.
     */
    private Integer nacionalidadId;

    /**
     * Fecha de nacimiento del usuario.
     * Opcional.
     */
    private LocalDate fechaNacimiento;

    /**
     * Género del usuario.
     * Opcional. Valores posibles: {@link GeneroEnum}.
     */
    private GeneroEnum genero;

    /**
     * Estado activo del usuario.
     * Opcional. True si el usuario está activo, false si está inactivo.
     */
    private Boolean activo;

    /**
     * URL o identificador de la foto de perfil del usuario.
     * Opcional.
     */
    private String fotoPerfil;

    /**
     * Información adicional o metadata asociada al usuario.
     * Opcional. Puede incluir cualquier par clave-valor.
     */
    private Map<String, Object> metadata;
}
