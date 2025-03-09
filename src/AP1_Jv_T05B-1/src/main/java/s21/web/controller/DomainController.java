package s21.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s21.domain.mapper.CurrentGameAIWebDomainMapper;
import s21.domain.mapper.CurrentGameWebDomainMapper;
import s21.domain.model.GameProcessor;
import s21.web.mapper.CurrentGameAIDomainWebMapper;
import s21.web.mapper.CurrentGameDomainWebMapper;
import s21.web.mapper.WinPlayerInfoDomainToWebMapper;
import s21.web.model.CurrentGameAIDTO;
import s21.web.model.CurrentGameDTO;
import s21.web.model.GameFieldDTO;
import s21.web.model.WinPlayerInfoDTO;

@Component
public class DomainController {
    @Autowired
    private GameProcessor logic;

    private final CurrentGameDomainWebMapper toWebMapper = CurrentGameDomainWebMapper.INSTANCE;
    private final CurrentGameAIDomainWebMapper toWebMapperAI = CurrentGameAIDomainWebMapper.INSTANCE;
    private final CurrentGameWebDomainMapper toDomainMapper = CurrentGameWebDomainMapper.INSTANCE;
    private final CurrentGameAIWebDomainMapper toDomainMapperAI = CurrentGameAIWebDomainMapper.INSTANCE;

    private final WinPlayerInfoDomainToWebMapper toWebMapperStats = WinPlayerInfoDomainToWebMapper.INSTANCE;


    public CurrentGameDTO findGameWithPlayer(String nickname) {
        return toWebMapper.domainToWeb(logic.findGame(nickname));
    }
    public CurrentGameAIDTO findGameWithAI(String nickname) {
        return toWebMapperAI.domainToWeb(logic.createGameWithAI(nickname));
    }
    public CurrentGameAIDTO updateFieldAndGetNextStep(CurrentGameAIDTO newField) throws Exception {
        CurrentGameAIDTO game = toWebMapperAI.domainToWeb(logic.nextStep(toDomainMapperAI.webToDomain(newField)));

        logic.updateGame(toDomainMapperAI.webToDomain(game));

        return game;
    }
    public CurrentGameDTO updateMultiplayerField(CurrentGameDTO newField) throws Exception {
        return toWebMapper.domainToWeb(logic.updateGame(toDomainMapper.webToDomain(newField)));
    }
    public List<String> getGames() {
        return logic.getAllGames();
    }
    public List<String> getMultiplayerGames() {
        return logic.getMultiplayerGames();
    }
    public List<String> getAIGames() {
        return logic.getAIGames();
    }
    public CurrentGameAIDTO initStartGame(UUID uuid) throws Exception {
        CurrentGameAIDTO game = toWebMapperAI.domainToWeb(logic.getAIGameByUUID(uuid));
        if(game.isX()) return game;
        else return updateFieldAndGetNextStep(game);
    }
    public UUID checkInFoundedList(String nickname) {
        return logic.checkInFoundedList(nickname);
    }
    public CurrentGameDTO getMultiplayerGameByUUID(String uuid) {
        return toWebMapper.domainToWeb(logic.getMultiplayerGameByUUID(UUID.fromString(uuid)));
    }
    public CurrentGameDTO checkFieldChanges(String uuid, GameFieldDTO field) throws Exception {
        return toWebMapper.domainToWeb(logic.checkFieldChanges(UUID.fromString(uuid), field.gameField()));
    }
    public UUID getPlayerUUID(String login) {
        return logic.getPlayerUuidByName(login);
    }
    public String getPlayerLogin(UUID uuid) {
        return logic.getPlayerLoginByUUID(uuid);
    }
    public CurrentGameDTO createMultiplayerGame(String nickname) {
        return toWebMapper.domainToWeb(logic.createMultiplayerGame(nickname));
    }
    public List<CurrentGameDTO> getAvailableGames() {
        return toWebMapper.domainToWeb(logic.getAvailableGames());
    }
    public void connectToGame(String uuid, String nickname) {
        logic.connectToGame(UUID.fromString(uuid), nickname);
    }
    public List<CurrentGameDTO> getEndGames(String uuid) {
        return toWebMapper.domainToWeb(logic.getEndedGamesMultiplayer(UUID.fromString(uuid)));
    }
    public List<WinPlayerInfoDTO> getTopPlayers(int N) {
        return toWebMapperStats.domainToWeb(logic.getTopPlayersMultiplayer(N));
    }
}
