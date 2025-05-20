package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.CommentPostRequest;
import co.edu.uniquindio.ingesis.restful.domain.Comment;
import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.domain.Project;
import co.edu.uniquindio.ingesis.restful.dtos.CommentResponse;
import co.edu.uniquindio.ingesis.restful.mappers.CommentMapper;
import co.edu.uniquindio.ingesis.restful.repositories.ICommentRepository;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import co.edu.uniquindio.ingesis.restful.repositories.IProjectRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    final CommentMapper commentMapper;

    @Inject
    ICommentRepository commentRepository;

    @Inject
    IUserRepository userRepository;

    @Inject
    IProjectRepository projectRepository;

    @Override
    @Transactional
    public CommentResponse createComment(CommentPostRequest request) {
        log.info("Creating comment for {}", request);

        User author = userRepository.findById(request.authorId());
        if (author == null) {
            throw new IllegalArgumentException("Author not found");
        }

        Project project = projectRepository.findById(request.projectId());
        if (project == null) {
            throw new IllegalArgumentException("Project not found");
        }

        Comment comment = new Comment();
        comment.setContent(request.content());
        comment.setCreatedAt(LocalDate.now());
        comment.setAuthor(author);
        comment.setProject(project);

        commentRepository.persist(comment);
        log.info("Created comment for {}", request);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    @Transactional
    public CommentResponse getCommentById(Long id) {
        log.info("Getting comment for {}", id);
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new IllegalArgumentException("Comment not found");
        }
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    @Transactional
    public List<CommentResponse> getCommentsByProjectId(int page, int size, Long projectId) {
        log.info("Getting comments for {}", projectId);
        return commentRepository.findByProjectId(projectId,page,size)
                .stream()
                .map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }
}
