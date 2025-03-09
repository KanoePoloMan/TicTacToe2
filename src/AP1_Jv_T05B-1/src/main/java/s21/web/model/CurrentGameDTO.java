package s21.web.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import s21.domain.model.GameState;

@Getter
@Setter
@AllArgsConstructor
public class CurrentGameDTO {
    private UUID uuid;
    private String X;
    private String O;
    private GameFieldDTO field;
    private GameState gameState;
    private String date;
    private String error;
}
