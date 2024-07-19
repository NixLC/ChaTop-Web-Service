package snx.rentals.api.model.dto;

public record JwtResponse(String jwt, long expiresIn) {
}
