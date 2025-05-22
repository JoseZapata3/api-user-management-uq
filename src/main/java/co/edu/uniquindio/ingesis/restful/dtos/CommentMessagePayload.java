package co.edu.uniquindio.ingesis.restful.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class CommentMessagePayload {
    private String email;
    private String comment;
    private String projectName;
    private String commentOwner;
}
