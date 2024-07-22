package snx.rentals.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.JwtResponse;
import snx.rentals.api.model.dto.LoginDto;
import snx.rentals.api.model.dto.UserDto;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.service.JwtService;
import snx.rentals.api.service.UserService;

import static snx.rentals.api.config.OpenApiConfig.BEARER_AUTH;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController extends GenericController<User> {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService users;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UserService service, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
    super(service);
    this.users = service;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public HttpEntity<DTO<User>> register(@Valid @RequestBody UserDto dto) {
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    DTO<User> created;
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

  @SecurityRequirement(name = BEARER_AUTH)
  @GetMapping("/me")
  public HttpEntity<DTO<User>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    DTO<User> user = ((User) userDetails).toDTO();
    return ResponseEntity.ok(user);
  }

  private UserDetails authenticate(LoginDto userCredentials) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword())
      );
    }
    catch (BadCredentialsException e) {
      throw new BadCredentialsException("Incorrect username or password", e);
    }
    return users.loadUserByUsername(userCredentials.getEmail());
  }
}
