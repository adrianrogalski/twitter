package pl.sda.twitter.dto;

import lombok.*;
import pl.sda.twitter.model.Hashtag;
import pl.sda.twitter.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetDtoOut {

    private long id;
    private String content;
    private int likes;
    private int retweets;
    private int comments;
    private Set<Hashtag> hashtagSet;
    private String username;
    private String formatedPublishingTime;
    private Boolean retweeted;
    private Boolean follows;

    public TweetDtoOut(String content, LocalDateTime publishingTime) {
        this.content = content;
        this.formatedPublishingTime = timeMapper(publishingTime);
    }

    private static String timeMapper(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter); // "1986-04-08 12:30"
    }

}
