package snx.rentals.api.service;


import org.springframework.stereotype.Service;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.repository.RentalRepository;

@Service
public class RentalServiceImpl extends AbstractGenericService<Rental> implements RentalService {
  private final UserService userService;

  public RentalServiceImpl(RentalRepository rentalRepo, UserService userService) {
    super(rentalRepo);
    this.userService = userService;
  }

  @Override
  public User findOwner(Integer id) {
    return userService.get(id);
  }

  @Override
  public Class<Rental> getEntityClass() {
    return Rental.class;
  }
}
