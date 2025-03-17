package co.edu.uniquindio.ingesis.restful.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record AuthResponse(String token) {}
