package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;

public record UserRegistrationResponse(
        @NotBlank
        String uuid,
        Role role
) {
}
