package snx.rentals.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import snx.rentals.api.model.entity.GenericEntity;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity<?>> extends JpaRepository<T, Integer> {
}
