package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import snx.rentals.api.model.entity.User;
import snx.rentals.api.model.view.DtoViews;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto implements DTO<User> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  @JsonView(DtoViews.Read.class)
  private Integer id;

  @Email(message = "Please enter a valid email address")
  @NotBlank(message = "Email address is required")
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private String email;

  @NotBlank(message = "A username is required and should not be blank")
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private String name;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank(message = "A password is required and should not be blank")
  @JsonView(DtoViews.Write.class)
  private String password;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  @JsonView(DtoViews.Read.class)
  private String createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  @JsonView(DtoViews.Read.class)
  private String updatedAt;

  @Override
  public User toEntity() {
    return User.builder()
      .id(id)
      .email(email)
      .name(name)
      .password(password)
      .build();
  }
}
