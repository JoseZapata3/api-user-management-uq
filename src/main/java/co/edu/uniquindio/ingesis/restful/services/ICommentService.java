package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.CommentPostRequest;
import co.edu.uniquindio.ingesis.restful.domain.Comment;
import co.edu.uniquindio.ingesis.restful.dtos.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(CommentPostRequest request);
    CommentResponse getCommentById(Long id);
    List<CommentResponse> getCommentsByProjectId(int page, int size, Long projectId);
}
