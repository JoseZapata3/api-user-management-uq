package co.edu.uniquindio.ingesis.restful.repositories;

import co.edu.uniquindio.ingesis.restful.domain.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface ICommentRepository extends PanacheRepository<Comment> {
}
