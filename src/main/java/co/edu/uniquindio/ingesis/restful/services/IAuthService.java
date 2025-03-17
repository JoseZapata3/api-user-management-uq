package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.AuthRequest;
import co.edu.uniquindio.ingesis.restful.dtos.AuthResponse;

public interface IAuthService {
    AuthResponse authenticate(AuthRequest request);
}
