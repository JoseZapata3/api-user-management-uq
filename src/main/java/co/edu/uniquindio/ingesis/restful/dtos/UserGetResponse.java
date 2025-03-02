package co.edu.uniquindio.ingesis.restful.dtos;

import co.edu.uniquindio.ingesis.restful.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserGetResponse(@Size(min=4,max = 20,message = "The username must have a minimum of 4 characters and a maximum of 20 characters.")
                              @NotBlank(message = "The username is required.")
                              String username,
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
                              @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "The date must be in the format YYYY-MM-DD (e.g., 2025-03-01).")
                              @NotBlank(message = "The date of birth is required")
                              String dateBirth,
                              Role role) {
}
