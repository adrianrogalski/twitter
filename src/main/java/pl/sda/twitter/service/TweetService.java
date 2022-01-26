package pl.sda.twitter.service;

import pl.sda.twitter.dto.TweetDto;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {
    Optional<Tweet> add(User user, TweetDto dto);
    List<TweetDtoOut> findAllTweets(long userId);
}
