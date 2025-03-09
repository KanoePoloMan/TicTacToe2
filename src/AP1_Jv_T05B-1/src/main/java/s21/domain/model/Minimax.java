package s21.domain.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Minimax {
    private final int playerSymbol;
    private final int botSymbol;

    public Minimax(int playerSymbol, int botSymbol) {
        this.playerSymbol = playerSymbol;
        this.botSymbol = botSymbol;
    }
    public Map.Entry<Integer, int[]> minimax(int[][] field, boolean isMax) {
        return this.minimax(field, isMax, 0);
    }
    private Map.Entry<Integer, int[]> minimax(int[][] field, boolean isMax, int depth) {
        if(gameIsEnded(field)) return new AbstractMap.SimpleEntry<>(evaluateGame(field, depth), null);

        int[] bestMove = new int[2];
        int bestValue;
        int symbol;

        if(isMax) {
            bestValue = Integer.MIN_VALUE;
            symbol = playerSymbol;
        } else {
            bestValue = Integer.MAX_VALUE;
            symbol = botSymbol;
        }
        for(int[] move : getNothingCells(field)) {
            int[][] newField = Arrays.stream(field).map(int[]::clone).toArray(int[][]::new);

            newField[move[0]][move[1]] = symbol;

            int hypothetical_value = minimax(newField, !isMax, depth + 1).getKey();
            if(isMax && hypothetical_value > bestValue) {
                bestValue = hypothetical_value;
                bestMove = new int[]{move[0], move[1]};
            } 
            if(!isMax && hypothetical_value < bestValue) {
                bestValue = hypothetical_value;
                bestMove = new int[]{move[0], move[1]};
            }
        }
        return new AbstractMap.SimpleEntry<>(bestValue, bestMove);
    }
    private boolean gameIsEnded(int [][] field) {
        return checkGameEnd(field, playerSymbol) 
            || checkGameEnd(field, botSymbol) 
            || getNothingCells(field).isEmpty();
    }
    private boolean checkGameEnd(int[][] field, int code) {
        boolean right = true;
        boolean left = true;
        for(int i = 0; i < 3; i++) {
            right &= (field[i][i] == code);
            left &= (field[3 - i - 1][i] == code);

            boolean rows = true;
            boolean cols = true;
            for(int j = 0; j < 3; j++) {
                cols &= (field[i][j] == code);
                rows &= (field[j][i] == code);
            }
            if(cols || rows) return true;
        }
        return right || left; 
    }
    private List<int[]> getNothingCells(int[][] cur) {
        List<int[]> result = new ArrayList<>();
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(cur[i][j] == 0) 
                    result.add(new int[]{i, j});
        return result;
    }
    private int evaluateGame(int [][] field, int depth) {
        if(checkGameEnd(field, botSymbol)) return depth - 10;
        else if(checkGameEnd(field, playerSymbol)) return 10 - depth;
        else return 0;
    }
}
