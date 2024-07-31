package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import snx.rentals.api.model.entity.Message;
import snx.rentals.api.model.view.DtoViews;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MessageDto implements DTO<Message>{

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  @JsonView({DtoViews.Read.class})
  private Integer id;

  @JsonProperty("rental_id")
  @NotNull
  @Digits(integer = 10, fraction = 0)
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private Integer rentalId;

  @JsonProperty("user_id")
  @NotNull
  @Digits(integer = 10, fraction = 0)
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private Integer userId;

  @NotBlank
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private String message;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  @JsonView({DtoViews.Read.class})
  private String createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  @JsonView({DtoViews.Read.class})
  private String updatedAt;

  @Override
  public Message toEntity() {
    return Message.builder()
        .id(id)
        .message(message)
        .build();
  }
}
