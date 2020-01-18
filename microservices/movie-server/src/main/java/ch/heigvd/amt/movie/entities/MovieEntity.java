package ch.heigvd.amt.movie.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Movie")
public class MovieEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private int year;
}
