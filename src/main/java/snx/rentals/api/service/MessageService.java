package snx.rentals.api.service;

import snx.rentals.api.model.entity.Message;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;

import java.util.Optional;

public interface MessageService extends GenericService<Message> {
  Rental findRental(Integer id);
  User findUser(Integer id);
}
