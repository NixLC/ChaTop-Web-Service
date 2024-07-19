package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import snx.rentals.api.model.entity.User;

@Data
@Builder
public class UserDto implements DTO<User> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  private Integer id;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String name;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank
  private String password;
  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  private String createdAt;
  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
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
