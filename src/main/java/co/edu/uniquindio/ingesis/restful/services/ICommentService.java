package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.CommentPostRequest;
import co.edu.uniquindio.ingesis.restful.domain.Comment;
import co.edu.uniquindio.ingesis.restful.dtos.CommentResponse;

public interface ICommentService {
    CommentResponse createComment(CommentPostRequest request);
}
