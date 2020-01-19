package ch.heigvd.amt.movie.api.endpoints;

import ch.heigvd.amt.movie.api.model.MovieWatched;
import ch.heigvd.amt.movie.entities.MovieWatchedEntity;
import ch.heigvd.amt.movie.repositories.MovieWatchedRepository;
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
public class MoviesWatchedApiController implements MoviesWatchedApi {
    @Autowired
    MovieWatchedRepository movieWatchedRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    public ResponseEntity<List<MovieWatched>> getWatchedMovies() {
        List<MovieWatched> moviesWatched = new ArrayList<>();
        for(MovieWatchedEntity movieWatchedEntity : movieWatchedRepository.findAll()) {
            moviesWatched.add(toMovieWatched(movieWatchedEntity));
        }
        return ResponseEntity.ok(moviesWatched);
    }

    private MovieWatched toMovieWatched(MovieWatchedEntity entity) {
        MovieWatched movieWatched = new MovieWatched();
        movieWatched.setMovieID((int) entity.getMovie().getId());
        movieWatched.setUserID((int) entity.getUser().getId());
        return movieWatched;
    }
}
