package ch.heigvd.amt.auth.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String password;

    @Column(columnDefinition = "bit(1) default 0")
    @Getter
    @Setter
    private boolean isAdmin;
}
