package co.edu.uniquindio.ingesis.restful.resources;

import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.services.IUserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResources {

    final IUserService userService;

    @POST
    public Response create(@Valid UserRegistrationRequest request){

        var response = userService.createUser(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

}
