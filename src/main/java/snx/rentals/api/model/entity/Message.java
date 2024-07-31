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
import snx.rentals.api.model.dto.MessageDto;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "messages")
public class Message implements Serializable, GenericEntity<Message> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_id", nullable = false)
  private Rental rental;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "message", length = 2000)
  private String message;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @Override
  public void update(Message entity) {
    this.rental = entity.getRental();
    this.user = entity.getUser();
    this.message = entity.getMessage();
  }

  @Override
  public DTO<Message> toDTO() {
    return MessageDto.builder()
     .id(id)
     .rentalId(rental.getId())
     .userId(user.getId())
     .message(message)
     .createdAt(DTO.instantToString(getCreatedAt(), DTO.DEFAULT_DATE_FORMATTER))
     .updatedAt(DTO.instantToString(getUpdatedAt(), DTO.DEFAULT_DATE_FORMATTER))
     .build();
  }
}