package snx.rentals.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.RentalDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rentals")
public class Rental implements Serializable, GenericEntity<Rental> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "surface", precision = 10)
  private BigDecimal surface;

  @Column(name = "price", precision = 10)
  private BigDecimal price;

  @Column(name = "picture")
  private String picture;

  @Column(name = "description", length = 2000)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @OneToMany(mappedBy = "rental")
  private Set<Message> messages = new LinkedHashSet<>();

  @Override
  public void update(Rental entity) {
    this.name = entity.getName();
    this.surface = entity.getSurface();
    this.price = entity.getPrice();
    this.picture = entity.getPicture();
    this.description = entity.getDescription();
    this.owner = entity.getOwner();
  }

  @Override
  public DTO<Rental> toDTO() {
    return RentalDto.builder()
      .id(id)
      .name(name)
      .surface(surface)
      .price(price)
      .pictureUrl(picture)
      .description(description)
      .ownerId(owner.getId())
      .createdAt(DTO.instantToString(getCreatedAt(), DTO.DEFAULT_DATE_FORMATTER))
      .updatedAt(DTO.instantToString(getUpdatedAt(), DTO.DEFAULT_DATE_FORMATTER))
      .build();
  }
}