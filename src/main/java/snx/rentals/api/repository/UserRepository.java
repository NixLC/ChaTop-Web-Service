package snx.rentals.api.repository;

import org.springframework.stereotype.Repository;
import snx.rentals.api.model.entity.User;

@Repository
public interface UserRepository extends GenericRepository<User> {
    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }
}
