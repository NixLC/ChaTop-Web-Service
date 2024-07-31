package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import snx.rentals.api.model.view.DtoViews;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView({DtoViews.Write.class})
public class LoginDto {
  @Email
  @NotBlank
  private String email;
  @NotBlank
  private String password;
}
