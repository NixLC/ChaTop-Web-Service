package snx.rentals.api.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import snx.rentals.api.model.dto.DTO;
import snx.rentals.api.model.dto.UserDto;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User implements Serializable, GenericEntity<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Rental> rentals = new LinkedHashSet<>();

    @Override
    public void update(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.messages.addAll(user.getMessages());
        this.rentals.addAll(user.getRentals());
    }

    @Override
    public User createNewInstance() {
        User newInstance = new User();
        newInstance.update(this);
        return newInstance;
    }

    @Override
    public DTO<User> toDTO() {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setCreatedAt(DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault()).format(createdAt));
        dto.setUpdatedAt(DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault()).format(updatedAt));
        return dto;
    }
}