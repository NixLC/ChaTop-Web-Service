package snx.rentals.api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import snx.rentals.api.model.entity.GenericEntity;
import snx.rentals.api.repository.GenericRepository;

@Getter
public abstract class AbstractGenericService<T extends GenericEntity<T>> implements GenericService<T> {
  private final GenericRepository<T> repository;
  private final String ENTITY = getEntityClass().getSimpleName();

  protected AbstractGenericService(GenericRepository<T> repository) {
    this.repository = repository;
  }

  @Override
  public Page<T> getPage(Pageable pageable) {
    Page<T> page = repository.findAll(pageable);
    if (!page.hasContent()) {
      throw new EntityNotFoundException("No " + ENTITY + " record found");
    }
    return page;
  }

  @Override
  public T get(Integer id) {
    return repository.findById(id)
                     .orElseThrow(() -> new EntityNotFoundException(ENTITY + " not found with id: " + id));
  }

  @Override
  @Transactional
  public T create(T newDomain) {
    // Prevent any update of record potentially having the same id
    if (newDomain.getId() != null) {
      throw new DataIntegrityViolationException("Candidate for " + ENTITY + " creation should have no id set");
    }
    try {
      return repository.save(newDomain);
    }
    catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Cannot create " + ENTITY + " because it violates some integrity constraints");
    }
  }

  @Override
  @Transactional
  public T update(T transcient) {
    T dbDomain = get(transcient.getId());
    dbDomain.update(transcient);
    try {
      return repository.save(dbDomain);
    }
    catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Cannot update " + ENTITY + " because it violates some integrity constraints");
    }
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    get(id);
    try {
      repository.deleteById(id);
    }
    catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Cannot delete " + ENTITY + " because it violates some integrity constraints");
    }
  }
}
