package s21.web.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import s21.domain.model.GameState;

@Getter
@Setter
@AllArgsConstructor
public class CurrentGameAIDTO {
    private UUID uuid;
    private String player;
    private boolean x;
    private GameFieldDTO field;
    private GameState state;
    // private LocalDateTime date;
    private String error;
}
