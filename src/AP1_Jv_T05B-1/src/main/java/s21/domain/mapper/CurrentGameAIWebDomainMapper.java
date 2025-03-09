package s21.domain.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.CurrentGameAI;
import s21.domain.model.GameField;
import s21.web.model.CurrentGameAIDTO;

@Mapper
public interface CurrentGameAIWebDomainMapper {
    CurrentGameAIWebDomainMapper INSTANCE = Mappers.getMapper(CurrentGameAIWebDomainMapper.class);

    default CurrentGameAI webToDomain(CurrentGameAIDTO web) {
        return new CurrentGameAI(
                        web.getUuid(),
                        UUID.fromString(web.getPlayer()),
                        web.isX(),
                        new GameField(web.getField().gameField()),
                        web.getState()
                   );
    }
}
