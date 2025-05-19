package co.edu.uniquindio.ingesis.restful.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentPostRequest(
        @NotBlank(message = "The comment must have content") String content,
        @NotNull(message = "The author ID must not be null") Long authorId,
        @NotNull(message = "The project ID must not be null") Long projectId
) {}