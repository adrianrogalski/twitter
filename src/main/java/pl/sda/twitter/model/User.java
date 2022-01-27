package pl.sda.twitter.model;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String email;
    @OneToMany
    private List<Tweet> tweets;
    @OneToMany
    private List<User> followers;
    @OneToMany
    private List<User> follow;
}
