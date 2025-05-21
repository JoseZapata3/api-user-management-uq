package co.edu.uniquindio.ingesis.restful.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ReportResponse(
        Long id,
        String title,
        String description,
        String code,
        String createdAt,
        Long ownerId,
        String ownerName,
        String type
) {}