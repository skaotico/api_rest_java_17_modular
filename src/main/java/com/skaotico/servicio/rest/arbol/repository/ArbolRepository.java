package com.skaotico.servicio.rest.arbol.repository;

import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h1>Repositorio ArbolRepository</h1>
 *
 * <p>
 * Esta interfaz es el repositorio de Spring Data JPA para la entidad {@link ArbolModel}.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * y consultas personalizadas sobre los árboles almacenados en la base de datos.
 * </p>
 *
 * <h2>Operaciones básicas heredadas de JpaRepository:</h2>
 * <ul>
 *     <li>save(entity): guarda o actualiza un árbol.</li>
 *     <li>findById(id): busca un árbol por su ID.</li>
 *     <li>findAll(): obtiene todos los árboles.</li>
 *     <li>deleteById(id): elimina un árbol por su ID.</li>
 *     <li>existsById(id): verifica si un árbol existe por su ID.</li>
 * </ul>
 *
 * <h2>Consultas personalizadas:</h2>
 * <ul>
 *     <li>{@link #findByEspecieContainingIgnoreCase(String)}: busca árboles cuya especie contenga
 *     el texto proporcionado, ignorando mayúsculas y minúsculas.</li>
 * </ul>
 *
 * <h2>Reglas de negocio:</h2>
 * <ul>
 *     <li>La búsqueda por especie permite coincidencias parciales, facilitando la búsqueda
 *     incluso si el nombre completo no se conoce.</li>
 *     <li>El repositorio garantiza operaciones atómicas y transaccionales en las operaciones CRUD
 *     básicas proporcionadas por JpaRepository.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * {@code
 * @Autowired
 * private ArbolRepository arbolRepository;
 *
 * // Guardar un árbol
 * ArbolModel arbol = new ArbolModel();
 * arbol.setEspecie("Quercus robur");
 * arbol.setAltura(5.0);
 * arbol.setUbicacion("Parque Central");
 * arbolRepository.save(arbol);
 *
 * // Buscar árboles por especie
 * List<ArbolModel> robles = arbolRepository.findByEspecieContainingIgnoreCase("robur");
 *
 * // Listar todos los árboles
 * List<ArbolModel> todos = arbolRepository.findAll();
 *
 * // Eliminar un árbol
 * arbolRepository.deleteById(arbol.getId());
 * }
 * </pre>
 *
 * @author Skaotico
 * @version 1.0
 * @since 2025-09-12
 */
@Repository
public interface ArbolRepository extends JpaRepository<ArbolModel, Long> {

    /**
     * Busca árboles cuya especie contenga el texto proporcionado, ignorando mayúsculas y minúsculas.
     *
     * <p>Esta búsqueda permite coincidencias parciales, por ejemplo, si la especie es
     * "Quercus robur" y se busca "robur", se retornará el árbol.</p>
     *
     * @param especie texto a buscar en la especie del árbol (obligatorio)
     * @return lista de {@link ArbolModel} que coincidan con el texto proporcionado
     */
    List<ArbolModel> findByEspecieContainingIgnoreCase(String especie);
}
