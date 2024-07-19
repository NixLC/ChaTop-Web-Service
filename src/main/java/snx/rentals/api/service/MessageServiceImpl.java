package snx.rentals.api.service;

import org.springframework.stereotype.Service;
import snx.rentals.api.model.entity.Message;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.repository.MessageRepository;

@Service
public class MessageServiceImpl extends AbstractGenericService<Message> implements MessageService {
  private final GenericService<User> userService;
  private final GenericService<Rental> rentalService;

  public MessageServiceImpl(MessageRepository messageRepo, RentalService rentalService, UserService userService) {
    super(messageRepo);
    this.userService = userService;
    this.rentalService = rentalService;
  }

  @Override
  public Rental findRental(Integer id) {
    return rentalService.get(id);
  }

  @Override
  public User findUser(Integer id) {
    return userService.get(id);
  }

  @Override
  public Class<Message> getEntityClass() {
    return Message.class;
  }
}
