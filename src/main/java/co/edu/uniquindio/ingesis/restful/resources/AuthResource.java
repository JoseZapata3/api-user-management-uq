package co.edu.uniquindio.ingesis.restful.resources;

import co.edu.uniquindio.ingesis.restful.dtos.AuthRequest;
import co.edu.uniquindio.ingesis.restful.dtos.AuthResponse;
import co.edu.uniquindio.ingesis.restful.services.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequest request) {
        AuthResponse authResponse = authService.authenticate(request);
        return Response.ok(authResponse).build();
    }
}
