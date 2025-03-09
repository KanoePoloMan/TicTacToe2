package s21.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.model.CurrentGameAIDAO;
import s21.domain.model.CurrentGameAI;
import s21.domain.model.GameState;

@Mapper
public interface CurrentGameAIDatasourceDomainMapper {
    CurrentGameAIDatasourceDomainMapper INSTANCE = Mappers.getMapper(CurrentGameAIDatasourceDomainMapper.class);
    GameFieldDatasourceDomainMapper gameFieldToDomain = GameFieldDatasourceDomainMapper.INSTANCE;

    default CurrentGameAI datasourceToDomain(CurrentGameAIDAO datasource) {
        return new CurrentGameAI(
                        datasource.getUuid(), 
                        datasource.getPlayer(), 
                        datasource.isX(), 
                        gameFieldToDomain.datasourceToDomain(datasource.getField()), 
                        GameState.valueOf(datasource.getState())
                   );
    }
}
