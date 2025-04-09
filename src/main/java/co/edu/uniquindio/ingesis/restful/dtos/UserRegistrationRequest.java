package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record UserRegistrationRequest(
        @Size(min=4,max = 20,message = "The username must have a minimum of 4 characters and a maximum of 20 characters.")
        @NotBlank(message = "The username is required.")
        String username,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "The password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one digit.")
        @Size(min = 8,message = "The minimum length is 8.")
        @NotBlank(message = "The password is required.")
        String password,
        @NotBlank(message = "The first name is required.")
        String firstName,
        @NotBlank(message = "The last name is required.")
        String lastName,
        @Email(message = "Must be a valid email address.")
        @NotBlank(message = "The email is required.")
        String email,
        @NotBlank(message = "The country is required.")
        String country,
        @NotBlank(message = "The phone prefix is required.")
        String phonePrefix,
        @NotBlank(message = "The phone is required.")
        String phone,
        @NotBlank(message = "The date of birth is required")
        String dateBirth,
        Role role
    ) {

    public UserRegistrationRequest {
        role = Objects.requireNonNullElse(role, Role.STUDENT);
    }

}
