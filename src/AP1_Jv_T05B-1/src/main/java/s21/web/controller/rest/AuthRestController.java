package s21.web.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import s21.domain.model.jwt.JwtRequest;
import s21.domain.model.jwt.JwtResponse;
import s21.domain.model.jwt.RefreshJwtRequest;
import s21.domain.service.AuthorizationService;
import s21.web.model.SignUpRequest;


@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthorizationService authorizationService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> auth(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authorizationService.authorization(authRequest);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/reg")
    public ResponseEntity<JwtResponse> registrationPost(@RequestBody SignUpRequest request) throws AuthException {
        final JwtResponse token = authorizationService.registration(request);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authorizationService.updateAccessToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> postMethodName(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authorizationService.updateRefreshToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }
}
