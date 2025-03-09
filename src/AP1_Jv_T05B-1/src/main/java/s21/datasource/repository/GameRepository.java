package s21.datasource.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import s21.datasource.model.CurrentGameDAO;

@Repository
public interface GameRepository extends CrudRepository<CurrentGameDAO, UUID> {
    Optional<CurrentGameDAO> findByUuid(UUID uuid);
    // Optional<CurrentGameDAO> findByState(String ended);

    List<CurrentGameDAO> findByState(String ended);

    List<CurrentGameDAO> findByX(UUID login);
    List<CurrentGameDAO> findByO(UUID login);

    @Query(value="SELECT * FROM games_multiplayer"
         + " WHERE o = :uuid AND (state = 'WIN_X' OR state = 'WIN_O' OR state = 'DRAW')"
         + " UNION ALL" 
         + " SELECT * FROM games_multiplayer"
         + " WHERE x = :uuid AND (state = 'WIN_X' OR state = 'WIN_O' OR state = 'DRAW')"
         + " ORDER BY date",
            nativeQuery=true)
    List<CurrentGameDAO> getEndedGamesByUUID(@Param("uuid") UUID uuid);
}
