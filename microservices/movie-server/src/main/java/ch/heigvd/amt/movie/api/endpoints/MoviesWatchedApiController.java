package ch.heigvd.amt.movie.api.endpoints;

import ch.heigvd.amt.movie.api.model.MovieWatched;
import ch.heigvd.amt.movie.entities.MovieWatchedEntity;
import ch.heigvd.amt.movie.repositories.MovieWatchedRepository;
import ch.heigvd.amt.movie.repositories.MovieRepository;
import ch.heigvd.amt.movie.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Api(tags = "moviesWatched")
public class MoviesWatchedApiController implements MoviesWatchedApi {
    @Autowired
    MovieWatchedRepository movieWatchedRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private HttpServletRequest request;

    public ResponseEntity<List<MovieWatched>> getWatchedMovies() {
        List<MovieWatched> moviesWatched = new ArrayList<>();
        long owner = (Long) request.getAttribute("userID");

        for(MovieWatchedEntity movieWatchedEntity : movieWatchedRepository.findAllByOwner(owner)) {
            moviesWatched.add(toMovieWatched(movieWatchedEntity));
        }
        return ResponseEntity.ok(moviesWatched);
    }

    public ResponseEntity<Object> createWatchedMovie(@ApiParam(value = "", required = true) @Valid @RequestBody MovieWatched movieWatched) {
        long owner = (Long) request.getAttribute("userID");

        // Check if user and movie exists
        if (userRepository.findById(Long.valueOf(movieWatched.getUserID())).isPresent() && movieRepository.findById(Long.valueOf(movieWatched.getMovieID())).isPresent()) {
            MovieWatchedEntity newMovieWatchedEntity = toMovieWatchedEntity(movieWatched, owner);
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
        Optional<MovieWatchedEntity> movieWatchedEntity = movieWatchedRepository.findById(Long.valueOf(id));
        long owner = (Long) request.getAttribute("userID");

        if (movieWatchedEntity.isPresent()) {
            if (owner != movieWatchedEntity.get().getOwner()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            movieWatchedRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MovieWatchedEntity toMovieWatchedEntity(MovieWatched movieWatched, long owner) {
        MovieWatchedEntity movieWatchedEntity = new MovieWatchedEntity();
        movieWatchedEntity.setMovie(movieRepository.findById(Long.valueOf(movieWatched.getMovieID())).get());
        movieWatchedEntity.setUser(userRepository.findById(Long.valueOf(movieWatched.getUserID())).get());
        movieWatchedEntity.setOwner(owner);
        return movieWatchedEntity;
    }

    private MovieWatched toMovieWatched(MovieWatchedEntity entity) {
        MovieWatched movieWatched = new MovieWatched();
        movieWatched.setMovieID((int) entity.getMovie().getId());
        movieWatched.setUserID((int) entity.getUser().getId());
        return movieWatched;
    }
}
