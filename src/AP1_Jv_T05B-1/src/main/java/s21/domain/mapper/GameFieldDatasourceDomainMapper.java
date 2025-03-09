package s21.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.GameField;

@Mapper
public interface GameFieldDatasourceDomainMapper {
    GameFieldDatasourceDomainMapper INSTANCE = Mappers.getMapper(GameFieldDatasourceDomainMapper.class);

    default GameField datasourceToDomain(String gameField) {
        char charField[] = gameField.toCharArray();
        int field[][] = new int[3][3];
        for(int i = 0; i < 9; i++) 
            field[i / 3][i % 3] = charField[i] - 48;

        return new GameField(field);
    }
}
