package co.edu.uniquindio.ingesis.restful.repositories;

import co.edu.uniquindio.ingesis.restful.domain.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface ICommentRepository extends PanacheRepository<Comment> {
    List<Comment> findByProjectId(Long projectId, int page, int size);
}
