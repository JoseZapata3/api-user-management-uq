package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.UserGetRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;
import co.edu.uniquindio.ingesis.restful.dtos.UserResponse;

import java.util.List;

public interface IUserService {
    UserRegistrationResponse createUser(UserRegistrationRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}
