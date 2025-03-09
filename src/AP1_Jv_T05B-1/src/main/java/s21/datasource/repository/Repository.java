package s21.datasource.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s21.datasource.model.CurrentGameAIDAO;
import s21.datasource.model.CurrentGameDAO;
import s21.datasource.model.UserDAO;
import s21.datasource.model.UserStatsDAO;
import s21.domain.model.GameState;
import s21.domain.model.WinPlayerInfo;

@Component
public class Repository {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepositoryAI gameRepositoryAI;
    @Autowired
    private SearchQueueRepository searchRepository;
    @Autowired
    private FoundedGamesRepository foundedGamesRepository;
    @Autowired
    private UserStatsRepository userStatsRepository;

    // public CurrentGameDAO getBlankGames() {
    //     return gameRepository.findByState(GameState.WAITING_PLAYERS.name()).orElse(null);
    // }
    public void saveNewGamePlayer(CurrentGameDAO game) {
        gameRepository.save(game);
    }
    public void replaceGamePlayer(CurrentGameDAO game) {
        gameRepository.deleteById(game.getUuid());
        gameRepository.save(game);
    }
    public void saveNewGameAI(CurrentGameAIDAO game) {
        gameRepositoryAI.save(game);
    }
    public void updateGameMultiplayer(CurrentGameDAO game) {
        gameRepository.save(game);
    }
    public void updateGameAI(CurrentGameAIDAO game) {
        gameRepositoryAI.save(game);
    }
    public List<CurrentGameDAO> getMultiplayerGames() {
        Iterable<CurrentGameDAO> iter = gameRepository.findAll();
        List<CurrentGameDAO> returned = new ArrayList<>();
        iter.forEach(returned::add);
        return returned;
    }
    public List<CurrentGameAIDAO> getAIGames() {
        Iterable<CurrentGameAIDAO> iter = gameRepositoryAI.findAll();
        List<CurrentGameAIDAO> returned = new ArrayList<>();
        iter.forEach(returned::add);
        return returned;
    }
    public List<CurrentGameDAO> getMultiplayerGamesForPlayer(String player) {
        UUID playerUUID = getUserUUIDbyLogin(player);
        List<CurrentGameDAO> returned = gameRepository.findByX(playerUUID);
        returned.addAll(gameRepository.findByO(playerUUID));
        return returned;
    }
    public List<CurrentGameAIDAO> getAIGamesForPlayer(String player) {
        UUID playerUUID = getUserUUIDbyLogin(player);
        List<CurrentGameAIDAO> returned = gameRepositoryAI.findByPlayer(playerUUID);
        return returned;
    }

    public UserDAO getUserByName(String name) {
        return userRepository.findByLogin(name).orElse(null);
    }
    public UserDAO getUserByUUID(UUID uuid) {
        return userRepository.findById(uuid).orElse(null);
    }
    public CurrentGameDAO getPlayerGameByUUID(UUID uuid) {
        return gameRepository.findByUuid(uuid).get();
    }
    public CurrentGameAIDAO getAIGameByUUID(UUID uuid) {
        return gameRepositoryAI.findByUuid(uuid).get();
    }
    public UUID getUserUUIDbyLogin(String login) {
        return userRepository.findByLogin(login).get().getUuid();
    }
    public UUID getSearchPlayer() {
        return searchRepository.getElem();
    }
    public void addSearchPlayer(UUID uuid) {
        searchRepository.add(uuid);
    }
    public void saveGameInFoundedRepository(UUID searcher, CurrentGameDAO game) {
        foundedGamesRepository.addToList(searcher, game);
    }
    public CurrentGameDAO checkInFoundedRepository(UUID player) {
        return foundedGamesRepository.checkInList(player);
    }
    public List<CurrentGameDAO> getAvailableGames() {
        return gameRepository.findByState(GameState.WAITING_PLAYERS.name());
    }
    public List<CurrentGameDAO> getEndedGamesMultiplayer(UUID uuid) {
        return gameRepository.getEndedGamesByUUID(uuid);
    }
    public List<WinPlayerInfo> getTopPlayersMultiplayer(int N) {
        final List<UserStatsDAO> temp = userStatsRepository.getTopPLayers(N);

        return temp.stream().map(elem -> {
            return new WinPlayerInfo(elem.getUuid(), 
                                     this.getUserByUUID(elem.getUuid()).getLogin(), 
                                     elem.getRatio());
            }).toList();
    }
    public void updateStat(CurrentGameDAO game) {
        UserStatsDAO statsX = userStatsRepository.findByUuid(game.getX()).orElse(null);
        UserStatsDAO statsO = userStatsRepository.findByUuid(game.getO()).orElse(null);

        if(statsX == null) statsX = new UserStatsDAO(game.getX(), 0, 0, 0, 0);
        if(statsO == null) statsO = new UserStatsDAO(game.getO(), 0, 0, 0, 0);

        if(game.getState().equals(GameState.WIN_X.name())) {
            statsX.setWins(statsX.getWins() + 1);
            statsO.setLoses(statsO.getLoses() + 1);

            statsX.setRatio((double)statsX.getWins() / (double)((statsX.getLoses() + statsX.getDraws()) == 0 ? 1 : statsX.getLoses() + statsX.getDraws()));
            statsO.setRatio((double)statsO.getWins() / (double)((statsO.getLoses() + statsO.getDraws()) == 0 ? 1 : statsO.getLoses() + statsO.getDraws()));

            userStatsRepository.save(statsX);
            userStatsRepository.save(statsO);
        } else if(game.getState().equals(GameState.WIN_O.name())) {
            statsX.setLoses(statsX.getLoses() + 1);
            statsO.setWins(statsO.getWins() + 1);

            statsX.setRatio((double)statsX.getWins() / (double)((statsX.getLoses() + statsX.getDraws()) == 0 ? 1 : statsX.getLoses() + statsX.getDraws()));
            statsO.setRatio((double)statsO.getWins() / (double)((statsO.getLoses() + statsO.getDraws()) == 0 ? 1 : statsO.getLoses() + statsO.getDraws()));

            userStatsRepository.save(statsX);
            userStatsRepository.save(statsO);
        } else if(game.getState().equals(GameState.DRAW.name())) {
            statsX.setDraws(statsX.getDraws() + 1);
            statsO.setDraws(statsX.getDraws() + 1);

            statsX.setRatio((double)statsX.getWins() / (double)((statsX.getLoses() + statsX.getDraws()) == 0 ? 1 : statsX.getLoses() + statsX.getDraws()));
            statsO.setRatio((double)statsO.getWins() / (double)((statsO.getLoses() + statsO.getDraws()) == 0 ? 1 : statsO.getLoses() + statsO.getDraws()));

            userStatsRepository.save(statsX);
            userStatsRepository.save(statsO);
        }
    }
}
