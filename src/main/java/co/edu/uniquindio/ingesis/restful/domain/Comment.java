package co.edu.uniquindio.ingesis.restful.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Comment {
    @NotBlank(message = "The comment must have content")
    private String content;
    private LocalDate createdAt;
    @NotBlank(message = "The comment must have author")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User author;
    @NotBlank(message = "The comment must belong to a project")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
