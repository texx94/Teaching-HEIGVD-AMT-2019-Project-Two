package ch.heigvd.amt.movie.api.endpoints;

import ch.heigvd.amt.movie.api.model.Movie;
import ch.heigvd.amt.movie.entities.MovieEntity;
import ch.heigvd.amt.movie.repositories.MovieRepository;
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
import java.util.Optional;

/**
 * Sptring controller that implements all operations generated by codegen
 * @author Gilliand Loris
 * @author Tutic Mateo
 * @version 1.0
 * @since 11.12.2019
 */
@Controller
@Api(tags = "movies")
public class MoviesApiController implements  MoviesApi {
    @Autowired
    MovieRepository movieRepository;

    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity movieEntity : movieRepository.findAll()) {
            movies.add(toMovie(movieEntity));
        }
        return ResponseEntity.ok(movies);
    }

    public ResponseEntity<Movie> getMovie(Integer id) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(Long.valueOf(id));
        if (movieEntity.isPresent()) {
            Movie movie = toMovie(movieEntity.get());
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> createMovie(@ApiParam(value = "", required = true) @Valid @RequestBody Movie movie) {
        MovieEntity newMovieEntity = toMovieEntity(movie);
        movieRepository.save(newMovieEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newMovieEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Void> deleteMovie(Integer id) {
        if (movieRepository.findById(Long.valueOf(id)).isPresent()) {
            movieRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> updateMovie(Integer id, @Valid @RequestBody Movie movie) {
        MovieEntity movieEntity = toMovieEntity(movie);
        if (movieRepository.findById(Long.valueOf(id)).isPresent()) {
            movieEntity.setId(id);
            movieRepository.save(movieEntity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MovieEntity toMovieEntity(Movie movie) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(movie.getTitle());
        entity.setYear(movie.getYear());
        return entity;
    }

    private Movie toMovie(MovieEntity entity) {
        Movie movie = new Movie();
        movie.setTitle(entity.getTitle());
        movie.setYear(entity.getYear());
        return movie;
    }
}
