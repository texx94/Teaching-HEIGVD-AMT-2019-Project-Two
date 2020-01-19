package ch.heigvd.amt.movie.repositories;

import ch.heigvd.amt.movie.entities.MovieLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface MovieLogRepository extends CrudRepository<MovieLogEntity, Long> {
}
