package snx.rentals.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController extends GenericController<User> {
    public UserController(UserRepository repository) {
        super(repository);
    }

    @Override
    public ResponseEntity<DTO<User>> get(Integer id) {
        User user = getOne(id).getBody();
        return ResponseEntity.ok(user.toDTO());
    }
}
