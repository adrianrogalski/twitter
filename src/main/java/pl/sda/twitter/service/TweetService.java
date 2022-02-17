package pl.sda.twitter.service;


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
    List<TweetDtoOut> findAllTweetsByUsername(String username);
    Optional<TweetCommentsPage> getTweetComments(long parentTweetId);
    Optional<Tweet> addComment(long parentTweetId, TweetDtoIn tweetDtoIn);
    List<TweetDtoOut> findAllTweetsContainingWords(String word);
    void addTweetLike(User user, long id);
    void deleteTweetById(long id);
    Optional<Tweet> addBookmark(User user, TweetDtoIn tweet);
    List<Tweet> findAllBookmarks();
}
