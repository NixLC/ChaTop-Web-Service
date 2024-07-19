package snx.rentals.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.repository.UserRepository;

@Service
public class UserServiceImpl extends AbstractGenericService<User> implements UserService {
  UserRepository userRepository;
  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.userRepository = repository;
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findByEmail(username);
  }

  @Override
  public Class<User> getEntityClass() {
    return User.class;
  }
}
