package co.edu.uniquindio.ingesis.restful.dtos;


import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class ActivationMessagePayload {

    private String email;
    private String activationCode;
    private String firstName;
}