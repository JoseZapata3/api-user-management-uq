package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.ActivationMessagePayload;
import co.edu.uniquindio.ingesis.restful.dtos.CommentMessagePayload;
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
import jakarta.json.bind.Jsonb;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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

    @Inject
    @Channel("comment-emitter")
    Emitter<byte[]> consumeCommentRequest;

    @Inject
    Jsonb jsonb;

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

        try {
            CommentMessagePayload payload = new CommentMessagePayload(
                    project.getOwner().getEmail(),
                    comment.getContent(),
                    project.getTitle(),
                    comment.getAuthor().getUsername()
            );

            log.info("Sending activation request: {}", payload);

            String jsonPayload = jsonb.toJson(payload);
            byte[] messageBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);

            log.info("Sending jsonPayload request: {}", jsonPayload);

            consumeCommentRequest.send(messageBytes)
                    .whenComplete((success, failure) -> { // Callback para saber si funcionó
                        if (failure != null) {
                            log.error("Failed to send activation message to Pulsar for comment {}: {}",
                                    comment.getContent(), failure.getMessage(), failure);
                        } else {
                            log.info("Activation message successfully sent to Pulsar for user {}", comment.getContent());
                        }
                    });

        } catch (Exception e) {
            // Captura cualquier error durante la creación/envío del mensaje
            log.error("Error preparing or sending Pulsar message for user {}: {}",
                    comment.getContent(), e.getMessage(), e);
        }

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
