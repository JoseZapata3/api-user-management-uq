package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.ActivationCode;
import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.dtos.*;
import co.edu.uniquindio.ingesis.restful.logging.AuditLog;
import co.edu.uniquindio.ingesis.restful.mappers.ActivationCodeMapper;
import co.edu.uniquindio.ingesis.restful.mappers.UserMapper;
import co.edu.uniquindio.ingesis.restful.repositories.ActivationCodeRepositoryImpl;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.json.bind.Jsonb;
import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    final UserMapper userMapper;
    final ActivationCodeMapper activationCodeMapper;
    final IUserRepository userRepository;
    final ActivationCodeRepositoryImpl activationCodeRepository;

    @Inject
    @Channel("activation-emitter")
    Emitter<byte[]> activationRequestEmitter;

    @Inject
    Jsonb jsonb;

    @Transactional
    public UserRegistrationResponse createUser(UserRegistrationRequest request) {
        log.info("Attempting to create user: {}", request.username());

        User user = userMapper.parseOf(request);
        user.setPassword(BcryptUtil.bcryptHash(request.password()));

        userRepository.persist(user);
        AuditLog.logAction(user.getUsername(),"User created: Username='{}'", user.getUsername());

        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);

        ActivationCodeCreation acc = new ActivationCodeCreation(code, expirationTime, false, user);
        ActivationCode activationCode = activationCodeMapper.parseOf(acc);
        activationCodeRepository.persist(activationCode);


        log.info("Activation code generated and persisted for user ID: {}", user.id);

        try {
            ActivationMessagePayload payload = new ActivationMessagePayload(
                    user.getEmail(),
                    activationCode.getCode(),
                    user.getFirstName()
            );

            log.info("Sending activation request: {}", payload);

            String jsonPayload = jsonb.toJson(payload);
            byte[] messageBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);

            log.info("Sending jsonPayload request: {}", jsonPayload);

            activationRequestEmitter.send(messageBytes)
                    .whenComplete((success, failure) -> { // Callback para saber si funcionó
                        if (failure != null) {
                            log.error("Failed to send activation message to Pulsar for user {}: {}",
                                    user.getEmail(), failure.getMessage(), failure);
                        } else {
                            log.info("Activation message successfully sent to Pulsar for user {}", user.getEmail());
                        }
                    });

        } catch (Exception e) {
            // Captura cualquier error durante la creación/envío del mensaje
            log.error("Error preparing or sending Pulsar message for user {}: {}",
                    user.getEmail(), e.getMessage(), e);
        }

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
