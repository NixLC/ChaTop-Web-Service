package snx.rentals.api.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.entity.GenericEntity;
import snx.rentals.api.service.GenericService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class GenericController<T extends GenericEntity<T>> {
  private final GenericService<T> service;
  protected final String COLLECTION_NAME;

  public GenericController(GenericService<T> service) {
    this.service = service;
    this.COLLECTION_NAME = service.getCollectionName();
  }

  public Map<String, List<DTO<T>>> getPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<DTO<T>> entities = service.getPage(pageable).map(GenericEntity::toDTO).toList();
    return Collections.singletonMap(COLLECTION_NAME, entities);
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
