package ch.heigvd.amt.auth.repositories;

import ch.heigvd.amt.auth.entites.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}