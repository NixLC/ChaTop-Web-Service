package snx.rentals.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
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
import snx.rentals.api.model.view.DtoViews;
import snx.rentals.api.service.JwtService;
import snx.rentals.api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService users;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UserService service, AuthenticationManager authenticationManager,
                        JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.users = service;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  @JsonView({DtoViews.Read.class})
  public HttpEntity<JwtResponse> register(@Valid
                                        @JsonView(DtoViews.Write.class)
                                        @RequestBody UserDto dto) {
    LoginDto credentials = new LoginDto(dto.getEmail(), dto.getPassword());
    try {
      dto.setPassword(passwordEncoder.encode(dto.getPassword()));
      users.create(dto.toEntity());
    }
    catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("A user with the same email already exists", e);
    }
    return login(credentials);
  }

  @PostMapping("/login")
  @JsonView({DtoViews.Read.class})
  public HttpEntity<JwtResponse> login(@Valid
                                       @JsonView({DtoViews.Write.class})
                                       @RequestBody LoginDto credentials) {
    UserDetails userDetails = authenticate(credentials);
    String jwtToken = jwtService.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(jwtToken, jwtService.getExpirationTime()));
  }

  @GetMapping("/me")
  @JsonView({DtoViews.Read.class})
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
