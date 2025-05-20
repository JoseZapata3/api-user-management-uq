package co.edu.uniquindio.ingesis.restful.resources;

import co.edu.uniquindio.ingesis.restful.dtos.CommentPostRequest;
import co.edu.uniquindio.ingesis.restful.dtos.CommentResponse;
import co.edu.uniquindio.ingesis.restful.services.ICommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/api/v1/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CommentResource {

    final ICommentService commentService;

    @POST
    @SecurityRequirement(name = "bearerAuth")
    @RolesAllowed({"STUDENT", "PROFESSOR"})
    public Response create(@Valid CommentPostRequest request) {
        CommentResponse response = commentService.createComment(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

}
