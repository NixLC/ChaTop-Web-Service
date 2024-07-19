package snx.rentals.api.model.entity;

import snx.rentals.api.model.dto.DTO;


public interface GenericEntity<T> {
    Integer getId();
    void update(T entity);
    default String getEntityName() {
        return this.getClass().getName();
    }
    DTO<T> toDTO();
}
