package s21.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameField {
    private final int[][] gameField;

    public GameField() {
        this.gameField = new int[3][3];
    }
}
