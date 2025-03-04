package co.edu.uniquindio.ingesis.restful.dtos;

import jakarta.validation.constraints.NotBlank;

public record ExecutionRequest(
        @NotBlank(message = "The code is required.")
        String code
) {
}
