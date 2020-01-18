package ch.heigvd.amt.movie.repositories;

import ch.heigvd.amt.movie.entities.MovieEntity;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieEntity, Long> {
}
