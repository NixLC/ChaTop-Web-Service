package snx.rentals.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import snx.rentals.api.model.entity.GenericEntity;

public interface GenericService<T extends GenericEntity<T>> {
  Page<T> getPage(Pageable pageable);

  T get(Integer id);

  @Transactional
  T create(T newDomain);

  @Transactional
  T update(T transcient);

  @Transactional
  void delete(Integer id);

  Class<T> getEntityClass();
  default String getCollectionName() {
    return getEntityClass().getSimpleName().toLowerCase().concat("s");
  }
}
