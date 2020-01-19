package ch.heigvd.amt.movie.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "movie_watched")
public class MovieWatchedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    @Getter
    @Setter
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_movie")
    @Getter
    @Setter
    private MovieEntity movie;

    @Getter
    @Setter
    private long owner;
}
