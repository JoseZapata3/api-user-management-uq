package co.edu.uniquindio.ingesis.restful.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class Sample extends PanacheEntity {
    @NotBlank(message = "The title is required.")
    private String title;
    @Size(max = 255, message = "The description cannot exceed 255 characters.")
    private String description;
    @NotBlank(message = "The source code is required.")
    private String code;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
