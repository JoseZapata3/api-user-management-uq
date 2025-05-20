package co.edu.uniquindio.ingesis.restful.repositories;

import co.edu.uniquindio.ingesis.restful.domain.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class CommentRepositoryImpl implements ICommentRepository{
    public List<Comment> findByProjectId(Long projectId, int page, int size) {
        return find("project.id", projectId)
                .page(page, size)
                .list();
    }
}
