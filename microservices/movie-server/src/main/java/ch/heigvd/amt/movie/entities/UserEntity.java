package ch.heigvd.amt.movie.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Getter
    private long id;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String firstname;
}
