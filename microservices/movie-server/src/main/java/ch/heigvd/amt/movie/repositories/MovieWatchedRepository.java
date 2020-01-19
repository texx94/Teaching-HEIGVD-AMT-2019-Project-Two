package ch.heigvd.amt.movie.repositories;

import ch.heigvd.amt.movie.entities.MovieWatchedEntity;
import org.springframework.data.repository.CrudRepository;

public interface MovieWatchedRepository extends CrudRepository<MovieWatchedEntity, Long> {
    Iterable<MovieWatchedEntity> findAllByOwner(long owner);
}
