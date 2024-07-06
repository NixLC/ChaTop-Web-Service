package snx.rentals.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import snx.rentals.api.model.entity.Rental;
import java.math.BigDecimal;

@Getter
@Setter
public class RentalDto implements DTO<Rental> {

  private Integer id;
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;
  @JsonProperty("owner_id")
  private Integer ownerId;
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("updated_at")
  private String updatedAt;

  @Override
  public Rental toPartialEntity() {
    Rental r =  new Rental();
    r.setName(name);
    r.setSurface(surface);
    r.setPrice(price);
    r.setPicture(picture);
    r.setDescription(description);
    return r;
  }
}
