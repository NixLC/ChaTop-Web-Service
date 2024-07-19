package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import snx.rentals.api.model.entity.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto implements DTO<Message>{
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  private Integer id;
  @JsonProperty("rental_id")
  @Digits(integer = 10, fraction = 0)
  private Integer rentalId;
  @JsonProperty("user_id")
  @Digits(integer = 10, fraction = 0)
  private Integer userId;
  @NotBlank
  private String message;
  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  private String createdAt;
  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  private String updatedAt;

  @Override
  public Message toEntity() {
    return Message.builder()
        .id(id)
        .message(message)
        .build();
  }
}
