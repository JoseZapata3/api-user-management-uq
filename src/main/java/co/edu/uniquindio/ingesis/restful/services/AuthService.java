package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.AuthRequest;
import co.edu.uniquindio.ingesis.restful.dtos.AuthResponse;
import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    final IUserRepository userRepository;

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.find("username", request.username()).firstResult();

        if (user == null || !io.quarkus.elytron.security.common.BcryptUtil.matches(request.password(), user.getPassword())) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        String token = Jwt.issuer("quarkus-jwt-auth")
                .subject(user.getUsername())
                .groups(Set.of(user.getRole().name()))
                .expiresAt(System.currentTimeMillis() / 1000 + 3600) // Expira en 1 hora
                .sign();


        return new AuthResponse(token);
    }
}
