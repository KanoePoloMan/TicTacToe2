package s21.datasource.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.GameField;

@Mapper
public interface GameFieldDomainDatasourceMapper {
    GameFieldDomainDatasourceMapper INSTANCE = Mappers.getMapper(GameFieldDomainDatasourceMapper.class);

    default String domainToDatasource(GameField gameField) {
        char returned[] = new char[9];
        for(int i = 0; i < 9; i++) {
            returned[i] = (char)(gameField.getGameField()[i / 3][i % 3] + 48);
        }
        return String.valueOf(returned);
    }
}
