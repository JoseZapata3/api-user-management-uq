package co.edu.uniquindio.ingesis.restful.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ProjectResponse(
        Long id,
        String title,
        String description,
        String code,
        String createdAt
) {
}
