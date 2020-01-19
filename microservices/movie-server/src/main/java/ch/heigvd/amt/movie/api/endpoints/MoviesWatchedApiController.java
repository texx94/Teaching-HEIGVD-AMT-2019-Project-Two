package ch.heigvd.amt.movie.api.endpoints;

import ch.heigvd.amt.movie.api.model.MovieWatched;
import ch.heigvd.amt.movie.entities.MovieWatchedEntity;
import ch.heigvd.amt.movie.repositories.MovieWatchedRepository;
import ch.heigvd.amt.movie.repositories.MovieRepository;
import ch.heigvd.amt.movie.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    public ResponseEntity<Object> createWatchedMovie(@ApiParam(value = "", required = true) @Valid @RequestBody MovieWatched movieWatched) {
        // Check if user and movie exists
        if (userRepository.findById(Long.valueOf(movieWatched.getUserID())).isPresent() && movieRepository.findById(Long.valueOf(movieWatched.getMovieID())).isPresent()) {
            MovieWatchedEntity newMovieWatchedEntity = toMovieWatchedEntity(movieWatched);
            movieWatchedRepository.save(newMovieWatchedEntity);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newMovieWatchedEntity.getId()).toUri();

            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Void> deleteWatchedMovie(Integer id) {
        if (movieWatchedRepository.findById(Long.valueOf(id)).isPresent()) {
            movieWatchedRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MovieWatchedEntity toMovieWatchedEntity(MovieWatched movieWatched) {
        MovieWatchedEntity movieWatchedEntity = new MovieWatchedEntity();
        movieWatchedEntity.setMovie(movieRepository.findById(Long.valueOf(movieWatched.getMovieID())).get());
        movieWatchedEntity.setUser(userRepository.findById(Long.valueOf(movieWatched.getUserID())).get());
        return movieWatchedEntity;
    }

    private MovieWatched toMovieWatched(MovieWatchedEntity entity) {
        MovieWatched movieWatched = new MovieWatched();
        movieWatched.setMovieID((int) entity.getMovie().getId());
        movieWatched.setUserID((int) entity.getUser().getId());
        return movieWatched;
    }
}
