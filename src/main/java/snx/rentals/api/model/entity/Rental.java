package snx.rentals.api.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import snx.rentals.api.model.dto.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "RENTALS")
public class Rental implements Serializable, GenericEntity<Rental> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
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
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.messages.addAll(entity.getMessages());
    }

    @Override
    public Rental createNewInstance() {
        Rental newInstance = new Rental();
        newInstance.update(this);
        return newInstance;
    }

    @Override
    public DTO<Rental> toDTO() {
        return null;
    }
}