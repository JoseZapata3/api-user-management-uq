package co.edu.uniquindio.ingesis.restful.dtos;

import jakarta.validation.constraints.NotBlank;

public record ErrorResponse(@NotBlank String type, @NotBlank String description) {

}