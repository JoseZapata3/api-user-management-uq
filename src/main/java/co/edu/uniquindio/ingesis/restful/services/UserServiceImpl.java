package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;
import co.edu.uniquindio.ingesis.restful.mappers.UserMapper;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    final UserMapper userMapper;
    final IUserRepository userRepository;

    @Transactional
    public UserRegistrationResponse createUser(UserRegistrationRequest request) {
        // Lógica para crear un usuario

        User user = userMapper.parseOf(request);
        userRepository.persist(user);
        return userMapper.toUserResponse(user);
    }
}
