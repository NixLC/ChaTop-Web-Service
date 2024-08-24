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
  @Email(message = "Please enter a valid email address")
  @NotBlank(message = "Email address is required")
  private String email;
  @NotBlank(message = "Password is required and should not be blank")
  private String password;
}
