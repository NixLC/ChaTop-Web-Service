package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
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
import snx.rentals.api.model.view.DtoViews;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RentalDto implements DTO<Rental> {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  @JsonView({DtoViews.Read.class})
  private Integer id;

  @NotBlank
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private String name;

  @NotNull
  @Digits(integer = 10, fraction = 2)
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private BigDecimal surface;

  @NotNull
  @Digits(integer = 10, fraction = 2)
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private BigDecimal price;

  @JsonProperty(value = "picture", access = JsonProperty.Access.READ_ONLY)
  @JsonSerialize(using = UrlSerializer.class)
  @JsonView({DtoViews.Read.class})
  private String pictureUrl;

  @NotBlank
  @JsonView({DtoViews.Read.class, DtoViews.Write.class})
  private String description;

  @JsonProperty(value = "owner_id", access = JsonProperty.Access.READ_ONLY)
  @Digits(integer = 10, fraction = 0)
  @JsonView({DtoViews.Read.class})
  private Integer ownerId;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
  @JsonView({DtoViews.Read.class})
  private String createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
  @JsonView({DtoViews.Read.class})
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
