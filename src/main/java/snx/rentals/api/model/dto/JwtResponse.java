package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import snx.rentals.api.model.view.DtoViews;

@JsonView({DtoViews.Read.class})
public record JwtResponse(String token, long expiresIn) {
}
