package s21.datasource.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.model.CurrentGameDAO;
import s21.domain.model.CurrentGame;

@Mapper
public interface CurrentGameDomainDatasourceMapper {
    CurrentGameDomainDatasourceMapper INSTANCE = Mappers.getMapper(CurrentGameDomainDatasourceMapper.class);
    GameFieldDomainDatasourceMapper GameFieldToDatasource = GameFieldDomainDatasourceMapper.INSTANCE;
    
    default CurrentGameDAO domainToDatasource(CurrentGame domain) {
        return new CurrentGameDAO(
                        domain.getUuid(), 
                        domain.getX(), 
                        domain.getO(), 
                        GameFieldToDatasource.domainToDatasource(domain.getGameField()),
                        domain.getGameState().name(),
                        domain.getDate()
                    );
    }



}
