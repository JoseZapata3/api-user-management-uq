package co.edu.uniquindio.ingesis.restful.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserGetRequest(
        @NotBlank(message="The id is required.")
        Long id
) {
}
