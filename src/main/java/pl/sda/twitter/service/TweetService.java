package pl.sda.twitter.service;


import pl.sda.twitter.dto.HashtagDto;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {
    Optional<Tweet> addNewTweet(User user, TweetDtoIn dto);
    List<TweetDtoOut> findAllTweets(long userId);
    Optional<TweetCommentsPage> getTweetComments(long parentTweetId);
    Optional<Tweet> addComment(long parentTweetId, TweetDtoIn tweetDtoIn);
    List<TweetDtoOut> findAllTweetsContainingWords(String word);
    TweetDtoOut addTweetLike(long id);
}
