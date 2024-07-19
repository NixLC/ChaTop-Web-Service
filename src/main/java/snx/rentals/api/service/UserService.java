package snx.rentals.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import snx.rentals.api.model.entity.User;

public interface UserService extends GenericService<User>, UserDetailsService {
  User findByEmail(String email);
}
