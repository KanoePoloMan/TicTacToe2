package s21.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.CurrentGameAI;
import s21.web.model.CurrentGameAIDTO;
import s21.web.model.GameFieldDTO;

@Mapper
public interface CurrentGameAIDomainWebMapper {
    CurrentGameAIDomainWebMapper INSTANCE = Mappers.getMapper(CurrentGameAIDomainWebMapper.class);

    default CurrentGameAIDTO domainToWeb(CurrentGameAI domain) {
        return new CurrentGameAIDTO(
                        domain.getUuid(), 
                        domain.getPlayer().toString(), 
                        domain.isX(),
                        new GameFieldDTO(domain.getField().getGameField()),
                        domain.getGameState(),
                        null
                    );
    }
}
