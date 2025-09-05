package com.skaotico.servicio.rest.util;

import java.util.List;

public interface BaseMapper<D, M> {
    M toModel(D dto);
    D toDto(M model);

    List<M> toModelList(List<D> dtoList);
    List<D> toDtoList(List<M> modelList);
}
