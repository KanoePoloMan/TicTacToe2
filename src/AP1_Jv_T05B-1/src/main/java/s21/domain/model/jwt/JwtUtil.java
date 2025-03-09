package s21.domain.model.jwt;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import s21.domain.model.Role;

public class JwtUtil {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUuid(UUID.fromString(claims.get("uuid", String.class)));
        jwtInfoToken.setUsername(claims.getSubject());

        return jwtInfoToken;
    }
    private static Set<Role> getRoles(Claims claims) {
        @SuppressWarnings("unchecked")
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }
}
