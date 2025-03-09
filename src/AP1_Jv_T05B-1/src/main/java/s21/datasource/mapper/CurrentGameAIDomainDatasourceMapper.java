package s21.datasource.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.model.CurrentGameAIDAO;
import s21.domain.model.CurrentGameAI;

@Mapper
public interface CurrentGameAIDomainDatasourceMapper {
    CurrentGameAIDomainDatasourceMapper INSTANCE = Mappers.getMapper(CurrentGameAIDomainDatasourceMapper.class);
    GameFieldDomainDatasourceMapper GameFieldToDatasource = GameFieldDomainDatasourceMapper.INSTANCE;

    default CurrentGameAIDAO domainToDatasource(CurrentGameAI domain) {
        return new CurrentGameAIDAO(
                        domain.getUuid(), 
                        domain.getPlayer(), 
                        domain.isX(), 
                        GameFieldToDatasource.domainToDatasource(domain.getField()), 
                        domain.getGameState().name()
                    );
    }
}
