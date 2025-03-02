package co.edu.uniquindio.ingesis.restful.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter

public class User extends PanacheEntity {

    @Size(min=4,max = 20,message = "The username must have a minimum of 4 characters and a maximum of 20 characters.")
    @NotBlank(message = "The username is required.")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "The password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one digit.")
    @Size(min = 8,message = "The minimum length is 8.")
    @NotBlank(message = "The password is required.")
    private String password;
    @NotBlank(message = "The first name is required.")
    private String firstName;
    @NotBlank(message = "The last name is required.")
    private String lastName;
    @Email(message = "Must be a valid email address.")
    @NotBlank(message = "The email is required.")
    private String email;
    @NotBlank(message = "The country is required.")
    private String country;
    @NotBlank(message = "The phone prefix is required.")
    private String phonePrefix;
    @NotBlank(message = "The phone is required.")
    private String phone;
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "The date must be in the format YYYY-MM-DD (e.g., 2025-03-01).")
    @NotBlank(message = "The date of birth is required")
    private LocalDate dateBirth;
    @NotBlank
    private Role role;


}
