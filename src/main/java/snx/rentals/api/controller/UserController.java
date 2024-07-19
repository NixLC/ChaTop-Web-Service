package snx.rentals.api.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.LoginDto;
import snx.rentals.api.model.dto.UserDto;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.model.dto.JwtResponse;
import snx.rentals.api.service.JwtService;
import snx.rentals.api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController extends GenericController<User> {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService users;

  public UserController(UserService service, AuthenticationManager authenticationManager, JwtService jwtService) {
    super(service);
    this.users = service;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public HttpEntity<DTO<User>> register(@Valid @RequestBody UserDto dto) {
    DTO<User> created = null;
    try {
      created = create(dto.toEntity());
    }
    catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("A user with the same email already exists", e);
    }
    return ResponseEntity.ok(created);
  }

  @PostMapping("/login")
  public HttpEntity<JwtResponse> login(@Valid @RequestBody LoginDto credentials) {
    UserDetails userDetails = authenticate(credentials);
    String jwtToken = jwtService.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(jwtToken, jwtService.getExpirationTime()));
  }

  @GetMapping("/me")
  public HttpEntity<DTO<User>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    DTO<User> user = ((User) userDetails).toDTO();
    return ResponseEntity.ok(user);
  }

  private UserDetails authenticate(LoginDto userCredentials) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userCredentials.getLogin(), userCredentials.getPassword())
      );
    }
    catch (BadCredentialsException e) {
      throw new BadCredentialsException("Incorrect username or password", e);
    }
    return users.loadUserByUsername(userCredentials.getLogin());
  }
}
