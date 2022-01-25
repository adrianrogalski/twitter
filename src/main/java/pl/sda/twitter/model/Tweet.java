package pl.sda.twitter.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    @ManyToMany
    private Set<Hashtag> hashtagSet;
    @ManyToOne
    private User author;
    private long respondId;
    private LocalDateTime publishingTime;

}
