package co.edu.uniquindio.ingesis.restful.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectResponse(
        @NotBlank(message = "The project ID is required.")
        String projectId,

        @Size(min = 3, max = 50, message = "The title must have between 3 and 50 characters.")
        @NotBlank(message = "The title is required.")
        String title,

        @Size(max = 255, message = "The description cannot exceed 255 characters.")
        String description,

        @NotBlank(message = "The source code is required.")
        String code,

        @NotBlank(message = "The creation date is required.")
        String createdAt
) {
}
