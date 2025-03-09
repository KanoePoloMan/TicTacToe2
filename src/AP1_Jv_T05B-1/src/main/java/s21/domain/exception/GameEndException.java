package s21.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import s21.domain.model.CurrentGame;

@RequiredArgsConstructor
public class GameEndException extends Exception {
    @Getter
    private final CurrentGame currentGame;

    public GameEndException(CurrentGame currentGame, String message) {
        super(message);
        this.currentGame = currentGame;
    }
}
