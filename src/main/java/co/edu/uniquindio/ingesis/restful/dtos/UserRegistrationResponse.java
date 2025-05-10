package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.Role;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record UserRegistrationResponse(
        Long id,
        Role role
) {
}
