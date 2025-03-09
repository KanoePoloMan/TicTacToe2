package s21.datasource.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameFieldDAO {
    private final int[][] gameField;

    public GameFieldDAO() {
        this.gameField = new int[3][3];
    }
}
