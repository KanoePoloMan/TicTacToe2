package s21.domain.exception;

import lombok.Getter;
import s21.domain.model.CurrentGameAI;

public class InvalidAIFieldException extends Exception {
    @Getter
    private final CurrentGameAI currentGame;

    public InvalidAIFieldException(CurrentGameAI currentGame, String message) {
        super(message);
        this.currentGame = currentGame;
    }
}
