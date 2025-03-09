package s21.datasource.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import s21.datasource.model.CurrentGameDAO;

@Component
public class FoundedGamesRepository {
    private final Map<UUID, CurrentGameDAO> founded = new HashMap<>();

    public void addToList(UUID uuid, CurrentGameDAO game) {
        founded.put(uuid, game);
    }
    public CurrentGameDAO checkInList(UUID uuid) {
        CurrentGameDAO game = founded.get(uuid);
        if(game != null) founded.remove(uuid);
        return game;
    }
}
