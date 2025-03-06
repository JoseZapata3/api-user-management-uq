package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;
import co.edu.uniquindio.ingesis.restful.dtos.UserResponse;
import co.edu.uniquindio.ingesis.restful.mappers.UserMapper;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    final UserMapper userMapper;
    final IUserRepository userRepository;

    @Transactional
    public UserRegistrationResponse createUser(UserRegistrationRequest request) {

        User user = userMapper.parseOf(request);
        userRepository.persist(user);
        return userMapper.toUserRegistrationResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.listAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }
}
