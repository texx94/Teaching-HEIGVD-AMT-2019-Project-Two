package ch.heigvd.amt.movie.api.endpoints;

import ch.heigvd.amt.movie.api.model.MovieWatched;
import ch.heigvd.amt.movie.entities.MovieLogEntity;
import ch.heigvd.amt.movie.repositories.MovieLogRepository;
import ch.heigvd.amt.movie.repositories.MovieRepository;
import ch.heigvd.amt.movie.repositories.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(tags = "moviesWatched")
public class MoviesLogApiController implements MoviesWatchedApi {
    @Autowired
    MovieLogRepository movieLogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    public ResponseEntity<List<MovieWatched>> getWatchedMovies() {
        List<MovieWatched> moviesLog = new ArrayList<>();
        for(MovieLogEntity movieLogEntity : movieLogRepository.findAll()) {
            moviesLog.add(toMovieWatched(movieLogEntity));
        }
        return ResponseEntity.ok(moviesLog);
    }

    private MovieWatched toMovieWatched(MovieLogEntity entity) {
        MovieWatched movieWatched = new MovieWatched();
        movieWatched.setMovieID((int) entity.getMovie().getId());
        movieWatched.setUserID((int) entity.getUser().getId());
        return movieWatched;
    }
}
