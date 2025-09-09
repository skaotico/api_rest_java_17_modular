package com.skaotico.servicio.rest.area.mapper;

import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;
import com.skaotico.servicio.rest.area.model.Area;
import com.skaotico.servicio.rest.area.model.TipoAreaEnum;
import com.skaotico.servicio.rest.util.BaseMapper;
import org.mapstruct.*;

/**
 * Mapper para convertir entre {@link CreateAreaDTO} y {@link Area}.
 * <p>
 * Este mapper se encarga de:
 * <ul>
 *     <li>Convertir DTO a entidad y viceversa.</li>
 *     <li>Ignorar el campo {@code id} al mapear a la entidad.</li>
 *     <li>Convertir el campo {@code tipoArea} entre {@link String} y {@link TipoAreaEnum}.</li>
 * </ul>
 */
@Mapper(componentModel = "spring")
public abstract class AreaMapper implements BaseMapper<CreateAreaDTO, Area> {

    /**
     * Convierte un {@link CreateAreaDTO} a {@link Area}.
     * <p>
     * El campo {@code id} se ignora intencionadamente porque se genera automáticamente en la base de datos.
     *
     * @param dto DTO con los datos del área a crear
     * @return entidad {@link Area} correspondiente
     */
    @Override
    @Mapping(target = "id", ignore = true)
    public abstract Area toModel(CreateAreaDTO dto);

    /**
     * Convierte una entidad {@link Area} a {@link CreateAreaDTO}.
     * <p>
     * Convierte el {@link TipoAreaEnum} a {@link String} usando {@code name()}.
     *
     * @param area entidad a convertir
     * @return DTO correspondiente
     */
    @Override
    @Mapping(target = "tipoArea", expression = "java(area.getTipoArea() != null ? area.getTipoArea().name() : null)")
    public abstract CreateAreaDTO toDto(Area area);

    /**
     * Método que se ejecuta después del mapeo del DTO a la entidad.
     * <p>
     * Convierte el campo {@code tipoArea} de {@link String} a {@link TipoAreaEnum}.
     * Si el valor del DTO no coincide con ningún enum válido, se asigna {@code null}.
     *
     * @param dto  DTO origen
     * @param area entidad destino
     */
    @AfterMapping
    protected void mapTipoArea(CreateAreaDTO dto, @MappingTarget Area area) {
        if (dto.getTipoArea() != null) {
            try {
                area.setTipoArea(TipoAreaEnum.valueOf(dto.getTipoArea().toUpperCase()));
            } catch (IllegalArgumentException e) {

                area.setTipoArea(null);
            }
        }
    }
}
