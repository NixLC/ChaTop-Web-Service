package snx.rentals.api.repository;

import org.springframework.stereotype.Repository;
import snx.rentals.api.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User> {
    Optional<User> findByEmail(String email);
}
