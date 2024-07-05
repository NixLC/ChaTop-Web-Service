package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import snx.rentals.api.model.entity.User;

@Getter
@Setter
public class UserDto implements DTO<User> {
  private Integer id;
  private String email;
  private String name;
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("updated_at")
  private String updatedAt;

  @Override
  public User toPartialEntity() {
    User user = new User();
    user.setEmail(email);
    user.setName(name);
    return user;
  }
}
