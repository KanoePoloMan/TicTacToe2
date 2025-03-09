package s21.web.model;

import java.util.Set;
import java.util.UUID;

import s21.domain.model.Role;

public record UserDTO(
    UUID uuid,
    String login,
    String password,
    Set<Role> roles) {}
