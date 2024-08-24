package snx.rentals.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.model.view.DtoViews;
import snx.rentals.api.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService users;
  public UserController(UserService service) {
    this.users = service;
  }

  @GetMapping("/{id}")
  @JsonView({DtoViews.Read.class})
  HttpEntity<DTO<User>> getOne(@PathVariable Integer id) {
    return ResponseEntity.ok(users.get(id).toDTO());
  }
}
