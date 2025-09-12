package com.skaotico.servicio.rest.arbol.service;

import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * <h1>Interfaz ArbolService</h1>
 *
 * <p>
 * Esta interfaz define los servicios principales para la gestión de árboles
 * en el sistema. Incluye operaciones de creación, eliminación, búsqueda y
 * listado de árboles, así como la gestión de imágenes asociadas a cada árbol.
 * </p>
 *
 * <h2>Reglas de negocio principales:</h2>
 * <ul>
 *     <li>No se permiten árboles duplicados con el mismo nombre y ubicación exacta.</li>
 *     <li>Las imágenes deben ser archivos válidos (jpg, png) y no superar el límite de tamaño permitido por la aplicación.</li>
 *     <li>La eliminación de un árbol solo se permite si existe en la base de datos.</li>
 * </ul>
 *
 * <h2>Formato y validaciones esperadas:</h2>
 * <ul>
 *     <li>ArbolCreateDto: debe incluir los campos obligatorios especie, altura y ubicación.</li>
 *     <li>MultipartFile para imágenes: tamaño máximo definido por la configuración de la aplicación.</li>
 *     <li>Los métodos que retornan Optional<ArbolModel> devuelven un Optional vacío si no se encuentra el árbol.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * {@code
 * @Autowired
 * private ArbolService arbolService;
 *
 * // Crear un árbol
 * ArbolCreateDto dto = new ArbolCreateDto();
 * dto.setEspecie("Quercus robur");
 * dto.setAltura(5.0);
 * dto.setUbicacion("Parque Central");
 * ArbolModel nuevoArbol = arbolService.crear(dto);
 *
 * // Subir imagen del árbol
 * MultipartFile file = ... // archivo recibido desde front-end
 * ImagenResponse imagen = arbolService.guardarImagen(file);
 *
 * // Listar todos los árboles
 * List<ArbolModel> arboles = arbolService.listarTodos();
 *
 * // Buscar árbol por ID
 * Optional<ArbolModel> arbolBuscado = arbolService.buscarPorId(nuevoArbol.getId());
 *
 * // Eliminar un árbol
 * boolean eliminado = arbolService.eliminar(nuevoArbol.getId());
 * }
 * </pre>
 *
 * @author
 * @version 1.0
 * @since 2025-09-12
 */
public interface ArbolService {

    /**
     * Guarda una imagen asociada a un árbol en el sistema.
     *
     * <p>Se espera que el archivo sea válido (jpg o png).
     * Puede lanzar excepciones si ocurre un error durante el almacenamiento.</p>
     *
     * @param file archivo de imagen del árbol (obligatorio)
     * @return {@link ImagenResponse} con la información de la imagen almacenada
     * @throws Exception si hay error al guardar la imagen
     */
    ImagenResponse guardarImagen(MultipartFile file) throws Exception;

    /**
     * Crea un nuevo árbol en la base de datos.
     *
     * <p>Todos los campos obligatorios del {@link ArbolCreateDto} deben estar completos.
     * No se permite crear árboles duplicados con misma especie y ubicación exacta.</p>
     *
     * @param arbol objeto con los datos del árbol a crear (obligatorio)
     * @return {@link ArbolModel} con los datos del árbol creado
     */
    ArbolModel crear(ArbolCreateDto arbol);

    /**
     * Elimina un árbol existente por su ID.
     *
     * <p>Solo se puede eliminar si el árbol existe.</p>
     *
     * @param id identificador del árbol a eliminar (obligatorio)
     * @return {@code true} si se eliminó correctamente, {@code false} si no existía
     */
    boolean eliminar(Long id);

    /**
     * Busca un árbol por su ID.
     *
     * @param id identificador del árbol a buscar (obligatorio)
     * @return {@link Optional} con el árbol encontrado o vacío si no existe
     */
    Optional<ArbolModel> buscarPorId(Long id);

    /**
     * Lista todos los árboles registrados en el sistema.
     *
     * @return lista de {@link ArbolModel}, puede estar vacía si no hay árboles
     */
    List<ArbolModel> listarTodos();

    /**
     * Lista los árboles que coincidan con la especie proporcionada.
     *
     * <p>La búsqueda es sensible a mayúsculas y minúsculas según la implementación
     * del repositorio.</p>
     *
     * @param especie nombre de la especie a buscar (obligatorio)
     * @return lista de {@link ArbolModel} que coincidan con la especie
     */
    List<ArbolModel> listarPorNombre(String especie);
}
