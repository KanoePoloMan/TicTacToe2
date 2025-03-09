package s21.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.CurrentGame;
import s21.web.model.CurrentGameDTO;
import s21.web.model.GameFieldDTO;

@Mapper
public interface CurrentGameDomainWebMapper {
    CurrentGameDomainWebMapper INSTANCE = Mappers.getMapper(CurrentGameDomainWebMapper.class);

    default CurrentGameDTO domainToWeb(CurrentGame domain) {
        if(domain == null) return null;
        return new CurrentGameDTO(
                        domain.getUuid(), 
                        domain.getX().toString(),
                        domain.getO() == null ? null : domain.getO().toString(),
                        new GameFieldDTO(domain.getGameField().getGameField()),
                        domain.getGameState(),
                        domain.getDate().toString(),
                        null);
    }
    default List<CurrentGameDTO> domainToWeb(List<CurrentGame> domain) {
        if(domain == null) return null;
        return domain.stream().map(this::domainToWeb).toList();
    }
}
