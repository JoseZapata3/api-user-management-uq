package co.edu.uniquindio.ingesis.restful.dtos;

import jakarta.validation.constraints.NotBlank;

public record ExecutionResponse(
        @NotBlank(message = "The execution ID is required.")
        String executionId,
        @NotBlank(message = "The project ID is required.")
        String projectId,
        @NotBlank(message = "The status is required.")
        String status,
        String output) {
}
