package snx.rentals.api.model.dto;

public record JwtResponse(String token, long expiresIn) {
}
