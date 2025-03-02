package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;

public interface IUserService {
    UserRegistrationResponse createUser(UserRegistrationRequest request);
}
