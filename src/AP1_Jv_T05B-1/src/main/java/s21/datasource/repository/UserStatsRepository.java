package s21.datasource.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import s21.datasource.model.UserStatsDAO;

@Repository
public interface UserStatsRepository extends CrudRepository<UserStatsDAO, UUID> {
    @Query(value="SELECT * FROM user_stats ORDER BY ratio DESC LIMIT :N",
           nativeQuery=true)
    List<UserStatsDAO> getTopPLayers(@Param("N") int N);

    Optional<UserStatsDAO> findByUuid(UUID uuid);
}
