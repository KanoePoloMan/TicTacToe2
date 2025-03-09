package s21.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CurrentGame {
    private final UUID uuid;
    
    private UUID X;
    private UUID O;
    
    private final GameField gameField;
    private GameState gameState = GameState.IDLE;

    private LocalDateTime date;

    public CurrentGame(GameField gameField) {
        this.uuid = UUID.randomUUID();
        this.gameField = gameField;
    }
}
