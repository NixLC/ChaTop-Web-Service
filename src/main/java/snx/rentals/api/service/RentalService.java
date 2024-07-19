package snx.rentals.api.service;

import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;

public interface RentalService extends GenericService<Rental> {
  User findOwner(Integer id);
}
