package ch.heigvd.amt.movie.repositories;

import ch.heigvd.amt.movie.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
