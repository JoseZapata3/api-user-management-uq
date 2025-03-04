package co.edu.uniquindio.ingesis.restful.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Getter
@Setter
public class Project extends PanacheEntity {
    private Long id;

    @Size(min = 3, max = 50, message = "The title must have between 3 and 50 characters.")
    @NotBlank(message = "The title is required.")
    private String title;

    @Size(max = 255, message = "The description cannot exceed 255 characters.")
    private String description;

    @NotBlank(message = "The source code is required.")
    private String code;

    private LocalDateTime createdAt;

}
