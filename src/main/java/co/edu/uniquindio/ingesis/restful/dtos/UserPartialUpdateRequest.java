package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPartialUpdateRequest(
        @Size(min=4,max = 20,message = "The username must have a minimum of 4 characters and a maximum of 20 characters.")
        String username,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "The password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one digit.")
        @Size(min = 8,message = "The minimum length is 8.")
        String password,
        String firstName,
        String lastName,
        @Email(message = "Must be a valid email address.")
        String email,
        String country,
        String phonePrefix,
        String phone,
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "The date must be in the format YYYY-MM-DD (e.g., 2025-03-01).")
        String dateBirth,
        Role role
) {
}
