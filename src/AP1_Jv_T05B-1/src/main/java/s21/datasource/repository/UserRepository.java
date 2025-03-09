package s21.datasource.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import s21.datasource.model.UserDAO;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, UUID> {
    Optional<UserDAO> findByUuid(UUID uuid);
    Optional<UserDAO> findByLogin(String login);
}
