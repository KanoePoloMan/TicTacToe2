package s21.domain.exception;

import lombok.Getter;
import s21.domain.model.CurrentGame;

public class InvalidFieldException extends Exception {
    @Getter
    private final CurrentGame currentGame;

    public InvalidFieldException(CurrentGame currentGame, String message) {
        super(message);
        this.currentGame = currentGame;
    }
}
