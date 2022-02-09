package pl.sda.twitter.mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.Tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TweetMapper {

    static public TweetDtoOut mapToTweetDtoOut(Tweet tweet) {
        return TweetDtoOut.builder()
                .content(tweet.getContent())
                .comments(tweet.getComments())
                .likes(tweet.getLikes())
                .comments(tweet.getComments())
                .username(tweet.getUsername())
                .publishingTime(timeMapper(tweet.getPublishingTime()))
                .build();
    }

    static public Tweet mapToTweet(TweetDtoIn dtoIn) {
        return Tweet.builder()
                .content(dtoIn.getContent())
                .likes(0)
                .comments(0)
                .retweets(0)
                .parentTweetId(-1)
                .authorId(dtoIn.getAuthor())
                .build();
    }

    public static String timeMapper(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter); // "1986-04-08 12:30"
    }


}