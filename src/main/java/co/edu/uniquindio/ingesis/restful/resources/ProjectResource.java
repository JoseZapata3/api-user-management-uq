package co.edu.uniquindio.ingesis.restful.resources;

import co.edu.uniquindio.ingesis.restful.dtos.ProjectCreateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectUpdateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectResponse;
import co.edu.uniquindio.ingesis.restful.services.IProjectService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/api/v1/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProjectResource {

    final IProjectService projectService;

    @POST
    public Response create(@Valid ProjectCreateRequest request) {
        var response = projectService.createProject(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        var response = projectService.getProjectById(id);
        return Response.ok(response).build();
    }

    @GET
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("size") @DefaultValue("10") int size) {
        List<ProjectResponse> projects = projectService.getAllProjects(page, size);
        return Response.ok(projects).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ProjectUpdateRequest request) {
        var response = projectService.updateProject(id, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        projectService.deleteProject(id);
        return Response.noContent().build();
    }
}
