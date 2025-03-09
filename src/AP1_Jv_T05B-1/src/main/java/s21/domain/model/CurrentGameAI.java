package s21.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentGameAI {
    private UUID uuid;
    private UUID player;
    private boolean x;
    private GameField field;
    private GameState gameState = GameState.IDLE;
    // private LocalDateTime date;
}
