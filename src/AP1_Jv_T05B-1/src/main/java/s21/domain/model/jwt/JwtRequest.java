package s21.domain.model.jwt;

public record JwtRequest(
    String username, 
    String password
) {}
