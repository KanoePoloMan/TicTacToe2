package s21.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import s21.domain.model.Role;
import s21.domain.model.User;
import s21.domain.model.jwt.JwtAuthentication;
import s21.domain.model.jwt.JwtProvider;
import s21.domain.model.jwt.JwtRequest;
import s21.domain.model.jwt.JwtResponse;
import s21.web.model.SignUpRequest;

@Service
public class AuthorizationService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    private final Map<String, String> refreshStorage = new HashMap<>();

    public JwtResponse registration(SignUpRequest request) throws AuthException {
        System.out.println("Custom registration func");
        try {
            userService.loadUserByUsername(request.username());
        } catch (UsernameNotFoundException e) {
            System.out.println("Custom registration");
            //register
            User newUser = new User();
            newUser.setUuid(UUID.randomUUID());
            newUser.setUsername(request.username());
            newUser.setPassword(passwordEncoder.encode(request.password()));
            newUser.setAuthorities(List.of(Role.USER));

            userService.saveUser(newUser);

            System.out.println("registrationSuccess");

            final String accessToken = jwtProvider.generateAccessToken(newUser);
            final String refreshToken = jwtProvider.generateRefreshToken(newUser);

            refreshStorage.put(newUser.getUsername(), refreshToken);

            return new JwtResponse(accessToken, refreshToken);
        }

        throw new AuthException("Invalid registration. User already exists");
    }
    public JwtResponse authorization(JwtRequest jwtRequest) {
        final User user = (User)userService.loadUserByUsername(jwtRequest.username());

        if(passwordEncoder.matches(jwtRequest.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(user.getUsername(), refreshToken);

            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new BadCredentialsException("Incorrect password");
        }
    }
    public JwtResponse updateAccessToken(String refreshToken) {
        if(jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            System.out.println("updateAccessToken login: " + login);
            final String saveRefreshToken = refreshStorage.get(login);

            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }
    public JwtResponse updateRefreshToken(String refreshToken) throws AuthException {
        if(jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);

            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User)userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(login, newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT Token");
    }
    public JwtAuthentication getAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
