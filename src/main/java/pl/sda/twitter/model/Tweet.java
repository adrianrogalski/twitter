package pl.sda.twitter.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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
    private int likes;
    private int retweets;
    @ManyToMany
    private List<Tweet> comments;
    @ManyToMany
    private Set<Hashtag> hashtagSet;
    @ManyToOne(cascade = CascadeType.ALL)
    private User author;
    private long respondId = 0;
    private LocalDateTime publishingTime;

}
