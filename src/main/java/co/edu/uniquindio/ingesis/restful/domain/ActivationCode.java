package co.edu.uniquindio.ingesis.restful.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "activation_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivationCode extends PanacheEntity {
    @NotBlank(message = "The code cannot be empty")
    @Column(nullable = false, unique = true)
    private String code;
    @NotNull(message = "Expiration time cannot be null")
    @Column(nullable = false)
    private LocalDateTime expires;
    @Column(nullable = false)
    private boolean activated;
    @NotNull(message = "The code must have an associated user")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}