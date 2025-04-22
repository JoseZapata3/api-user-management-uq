package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActivationCodeCreation(
        @NotBlank(message = "The code cannot be empty")
        @Column(nullable = false, unique = true)
        String code,
        @NotNull(message = "Expiration time cannot be null")
        @Column(nullable = false)
        LocalDateTime expires,
        @Column(nullable = false)
        boolean activated,
        @NotNull(message = "The code must have an associated user")
        @OneToOne(fetch = FetchType.LAZY, optional = false)
        User user
) {
}
