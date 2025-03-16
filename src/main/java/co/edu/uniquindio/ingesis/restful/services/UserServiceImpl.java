package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.dtos.UserPartialUpdateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;
import co.edu.uniquindio.ingesis.restful.dtos.UserResponse;
import co.edu.uniquindio.ingesis.restful.mappers.UserMapper;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
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

        user.setPassword(BcryptUtil.bcryptHash(request.password()));

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

    public List<UserResponse> getAllUsers(int page, int size) {
        return userRepository.findAll()
                .page(page, size)
                .list()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRegistrationRequest request) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        user.setUsername(request.username());
        user.setPassword(BcryptUtil.bcryptHash(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setCountry(request.country());
        user.setPhonePrefix(request.phonePrefix());
        user.setPhone(request.phone());
        user.setDateBirth(LocalDate.parse(request.dateBirth()));
        user.setRole(request.role());

        userRepository.persist(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse partialUpdateUser(Long id, UserPartialUpdateRequest request) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        if (request.username() != null) user.setUsername(request.username());
        if (request.password() != null) user.setPassword(BcryptUtil.bcryptHash(request.password()));
        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.email() != null) user.setEmail(request.email());
        if (request.country() != null) user.setCountry(request.country());
        if (request.phonePrefix() != null) user.setPhonePrefix(request.phonePrefix());
        if (request.phone() != null) user.setPhone(request.phone());
        if (request.dateBirth() != null) user.setDateBirth(LocalDate.parse(request.dateBirth()));
        if (request.role() != null) user.setRole(request.role());

        userRepository.persist(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        userRepository.delete(user);
    }


}
