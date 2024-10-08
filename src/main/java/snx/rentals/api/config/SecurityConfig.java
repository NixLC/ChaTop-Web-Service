package snx.rentals.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import snx.rentals.api.security.JwtRequestFilter;

/**
 * Adapted from <a href="https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac">...</a>
 */
@Configuration
public class SecurityConfig {
  private final JwtRequestFilter jwtRequestFilter;
  private final Http401UnauthorizedEntryPoint entryPoint;

  public static final String RENTAL_UPLOAD_WEB = "/img/rentals";

  public SecurityConfig(JwtRequestFilter jwtRequestFilter, Http401UnauthorizedEntryPoint entryPoint) {
    this.jwtRequestFilter = jwtRequestFilter;
    this.entryPoint = entryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.requestMatchers(
          "/api/auth/register",
          "/api/auth/login",
            RENTAL_UPLOAD_WEB +"/**",
          "/openapi.yml",

          "v3/api-docs",
          "v3/api-docs/**",
          "swagger-ui/**",
          "/swagger-ui.html",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/webjars/**"
        ).permitAll()
        .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(entryPoint));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    return auth.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}