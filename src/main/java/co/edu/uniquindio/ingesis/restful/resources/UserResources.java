package co.edu.uniquindio.ingesis.restful.resources;

import co.edu.uniquindio.ingesis.restful.dtos.UserPartialUpdateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserResponse;
import co.edu.uniquindio.ingesis.restful.services.IUserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResources {

    final IUserService userService;

    @POST
    @SecurityRequirement(name = "bearerAuth")
    public Response create(@Valid UserRegistrationRequest request){
        var response = userService.createUser(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"PROFESSOR","STUDENT"})
    @SecurityRequirement(name = "bearerAuth")
    public Response getById(@PathParam("id") Long id) {
        UserResponse user = userService.getUserById(id);
        return Response.ok(user).build();
    }

    @GET
    @RolesAllowed({"PROFESSOR","STUDENT"})
    @SecurityRequirement(name = "bearerAuth")
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
                           @QueryParam("size") @DefaultValue("10") int size) {
        List<UserResponse> users = userService.getAllUsers(page, size);

        if (users.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok(users).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"PROFESSOR","STUDENT"})
    @SecurityRequirement(name = "bearerAuth")
    public Response updateUser(@PathParam("id") Long id, @Valid UserRegistrationRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return Response.ok(updatedUser).build();
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({"PROFESSOR","STUDENT"})
    @SecurityRequirement(name = "bearerAuth")
    public Response partialUpdateUser(@PathParam("id") Long id, UserPartialUpdateRequest request) {
        UserResponse updatedUser = userService.partialUpdateUser(id, request);
        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"PROFESSOR"})
    @SecurityRequirement(name = "bearerAuth")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.noContent().build();
    }


}
