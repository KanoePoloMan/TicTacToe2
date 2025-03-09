package s21.domain.model;

import java.util.UUID;

public record WinPlayerInfo(
    UUID uuid,
    String login,
    double ratio
) {}
