package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.Role;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@RegisterForReflection
public record UserResponse(
        String username,
        String firstName,
        String lastName,
        String email,
        String country,
        String phonePrefix,
        String phone,
        String dateBirth,
        Role role
){ }
