package snx.rentals.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.WrapperDto;
import snx.rentals.api.model.entity.GenericEntity;
import snx.rentals.api.service.GenericService;

public abstract class GenericController<T extends GenericEntity<T>> {
  private final GenericService<T> service;
  protected final String COLLECTION_NAME;

  public GenericController(GenericService<T> service) {
    this.service = service;
    this.COLLECTION_NAME = service.getCollectionName();
  }

  public WrapperDto<T> getPage(int page, int size) {
    Pageable pageable = PageRequest.of(page -1, size);
    Page<DTO<T>> dtos = service.getPage(pageable).map(GenericEntity::toDTO);
    return new WrapperDto<>(dtos, COLLECTION_NAME);
  }

  public DTO<T> get(Integer id) {
    T entity = service.get(id);
    return entity.toDTO();
  }

  public DTO<T> create(T candidate) {
    return service.create(candidate).toDTO();
  }

  public DTO<T> update(T candidate) {
    return service.update(candidate).toDTO();
  }

  public boolean delete(Integer id) {
    service.delete(id);
    return true;
  }
}
