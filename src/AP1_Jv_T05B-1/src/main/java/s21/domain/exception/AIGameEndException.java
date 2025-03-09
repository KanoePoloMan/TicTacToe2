package s21.domain.exception;

import lombok.Getter;
import s21.domain.model.CurrentGameAI;

public class AIGameEndException extends Exception {
    @Getter
    private final CurrentGameAI currentGame;

    public AIGameEndException(CurrentGameAI currentGame, String message) {
        super(message);
        this.currentGame = currentGame;
    }
}
