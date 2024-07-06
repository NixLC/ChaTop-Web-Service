package snx.rentals.api.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.MessageDto;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "MESSAGES")
public class Message implements Serializable, GenericEntity<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message", length = 2000)
    private String message;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Override
    public void update(Message entity) {
        this.rental = entity.getRental();
        this.user = entity.getUser();
        this.message = entity.getMessage();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    @Override
    public Message createNewInstance() {
        Message newInstance = new Message();
        newInstance.update(this);
        return newInstance;
    }

    @Override
    public DTO<Message> toDTO() {
        MessageDto dto = new MessageDto();
        dto.setId(id);
        dto.setRentalId(rental.getId());
        dto.setUserId(user.getId());
        dto.setMessage(message);
        dto.setCreatedAt(DateTimeFormatter.ofPattern("yyyy/MM/dd")
                                          .withZone(ZoneId.systemDefault())
                                          .format(createdAt));
        dto.setUpdatedAt(DateTimeFormatter.ofPattern("yyyy/MM/dd")
                                          .withZone(ZoneId.systemDefault())
                                          .format(updatedAt));
        return dto;
    }
}