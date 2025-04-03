package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.dtos.UserPartialUpdateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;
import co.edu.uniquindio.ingesis.restful.dtos.UserResponse;
import co.edu.uniquindio.ingesis.restful.logging.AuditLog;
import co.edu.uniquindio.ingesis.restful.mappers.UserMapper;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    final UserMapper userMapper;
    final IUserRepository userRepository;

    @Transactional
    public UserRegistrationResponse createUser(UserRegistrationRequest request) {
        log.info("Attempting to create user: {}", request.username());

        User user = userMapper.parseOf(request);
        user.setPassword(BcryptUtil.bcryptHash(request.password()));

        userRepository.persist(user);
        AuditLog.logAction(user.getUsername(),"User created: Username='{}'", user.getUsername());

        return userMapper.toUserRegistrationResponse(user);
    }

    public UserResponse getUserById(Long id) {
        log.debug("Searching for user with ID: {}", id);

        User user = userRepository.findById(id);
        if (user == null) {
            log.warn("User not found with ID: {}", id);
            throw new NotFoundException("User not found with ID: " + id);
        }

        log.trace("User found: {}", user);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers(int page, int size) {
        log.debug("Retrieving user list (page={}, size={})", page, size);

        return userRepository.findAll()
                .page(page, size)
                .list()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRegistrationRequest request) {
        log.info("Attempting to update user with ID: {}", id);

        User user = userRepository.findById(id);
        if (user == null) {
            log.warn("User not found for update with ID: {}", id);
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
        AuditLog.AUDIT_LOG.warn("User updated: ID={}", id);

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse partialUpdateUser(Long id, UserPartialUpdateRequest request) {
        log.info("Attempting partial update of user with ID: {}", id);

        User user = userRepository.findById(id);
        if (user == null) {
            log.warn("User not found for partial update with ID: {}", id);
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
        AuditLog.AUDIT_LOG.warn("User partially updated: ID={}", id);

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Attempting to delete user with ID: {}", id);

        User user = userRepository.findById(id);
        if (user == null) {
            log.warn("User not found for deletion with ID: {}", id);
            throw new NotFoundException("User not found with ID: " + id);
        }

        userRepository.delete(user);
        AuditLog.AUDIT_LOG.error("User deleted: ID={}, Username='{}'", id, user.getUsername());
    }


}
