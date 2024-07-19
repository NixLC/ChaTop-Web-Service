package snx.rentals.api.repository;

import org.springframework.stereotype.Repository;
import snx.rentals.api.model.entity.Rental;

@Repository
public interface RentalRepository extends GenericRepository<Rental> {}
