package co.edu.uniquindio.ingesis.restful.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Comment extends PanacheEntity {
    @NotBlank(message = "The comment must have content")
    private String content;
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
