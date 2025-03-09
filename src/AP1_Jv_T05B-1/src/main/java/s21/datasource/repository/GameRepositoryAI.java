package s21.datasource.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import s21.datasource.model.CurrentGameAIDAO;

@Repository
public interface GameRepositoryAI extends CrudRepository<CurrentGameAIDAO, UUID> {
    List<CurrentGameAIDAO> findByPlayer(UUID player);
    Optional<CurrentGameAIDAO> findByUuid(UUID uuid);
}
