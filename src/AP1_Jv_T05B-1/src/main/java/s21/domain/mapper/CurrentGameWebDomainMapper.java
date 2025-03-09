package s21.domain.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.CurrentGame;
import s21.domain.model.GameField;
import s21.web.model.CurrentGameDTO;

@Mapper
public interface CurrentGameWebDomainMapper {
    CurrentGameWebDomainMapper INSTANCE = Mappers.getMapper(CurrentGameWebDomainMapper.class);

    default CurrentGame webToDomain(CurrentGameDTO web) {
        return new CurrentGame(
                    web.getUuid(),
                    UUID.fromString(web.getX()),
                    UUID.fromString(web.getO()),
                    new GameField(web.getField().gameField()),
                    web.getGameState(),
                    LocalDateTime.parse(web.getDate()));
    }
}
