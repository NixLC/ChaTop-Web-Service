package snx.rentals.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.service.UserService;

import static snx.rentals.api.config.OpenApiConfig.BEARER_AUTH;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users")
@SecurityRequirement(name = BEARER_AUTH)
public class UserController extends GenericController<User> {

  public UserController(UserService service) {
    super(service);
  }

  @Operation(
      description = "Get a user from his id"
  )
  @GetMapping("/{id}")
  HttpEntity<DTO<User>> getOne(@PathVariable Integer id) {
    return ResponseEntity.ok(get(id));
  }
}
