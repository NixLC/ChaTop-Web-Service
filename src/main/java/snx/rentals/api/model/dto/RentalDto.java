package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import snx.rentals.api.model.entity.Rental;
import snx.rentals.api.model.serializer.UrlSerializer;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDto implements DTO<Rental> {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  private Integer id;
  @NotBlank
  private String name;
  @NotNull
  @Digits(integer = 10, fraction = 2)
  private BigDecimal surface;
  @NotNull
  @Digits(integer = 10, fraction = 2)
  private BigDecimal price;
  @JsonSerialize(using = UrlSerializer.class)
  @JsonProperty(value= "picture" , access = JsonProperty.Access.READ_ONLY)
  private String pictureUrl;
  @NotBlank
  private String description;
  @Digits(integer = 10, fraction = 0)
  @JsonProperty(value = "owner_id", access = JsonProperty.Access.READ_ONLY)
  private Integer ownerId;
  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  private String createdAt;
  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  private String updatedAt;

  @Override
  public Rental toEntity() {
    return Rental.builder()
      .id(id)
      .name(name)
      .surface(surface)
      .price(price)
      .picture(pictureUrl)
      .description(description)
      .build();
  }
}
