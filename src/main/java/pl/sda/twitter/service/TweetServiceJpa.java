package pl.sda.twitter.service;

import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.mapper.TweetMapper;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaTweetRepository;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetServiceJpa implements TweetService{
    private final JpaTweetRepository jpaTweetRepository;
    public final static int NOT_A_COMMENT_TWEET_ID = -1;
    public final TweetMapper tweetMapper;

    public TweetServiceJpa(JpaTweetRepository jpaTweetRepository, TweetMapper tweetMapper) {
        this.jpaTweetRepository = jpaTweetRepository;
        this.tweetMapper = tweetMapper;
    }

    @Override
    @Transactional
    public Optional<Tweet> addNewTweet(User user, TweetDtoIn dto) {
        Tweet tweet = Tweet.builder()
                .authorId(user.getId())
                .username(user.getUsername())
                .content(dto.getContent())
                .publishingTime(now())
                .retweets(0)
                .likes(0)
                .parentTweetId(NOT_A_COMMENT_TWEET_ID)
                .build();
        Tweet savedTweet = jpaTweetRepository.save(tweet);
        return Optional.ofNullable(savedTweet);
    }

    @Override
    public List<TweetDtoOut> findAllTweets(long userId) {
        List<Tweet> tweets = jpaTweetRepository.findAllByAuthorId(userId);
        return tweets.stream().map(tweet ->
            TweetMapper.mapToTweetDtoOut(tweet)
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<TweetCommentsPage> getTweetComments(long parentTweetId) {
        Tweet tweet = jpaTweetRepository.getById(parentTweetId);
        List<Tweet> comments = jpaTweetRepository.findAllByParentTweetId(parentTweetId);
        if (comments.isEmpty()) {
            return Optional.empty();
        }
        TweetDtoOut tweetDtoOut = TweetMapper.mapToTweetDtoOut(tweet);
        TweetCommentsPage tweetCommentsPage = new TweetCommentsPage(tweetDtoOut, comments);
        return Optional.ofNullable(tweetCommentsPage);
    }

    @Override
    @Transactional
    public Optional<Tweet> addComment(long parentTweetId, TweetDtoIn tweetDtoIn) {
        Tweet commentTweet = TweetMapper.mapToTweet(tweetDtoIn);
        commentTweet.setParentTweetId(parentTweetId);
        Tweet savedTweetComment = jpaTweetRepository.save(commentTweet);
        return Optional.ofNullable(savedTweetComment);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public List<TweetDtoOut> findAllTweetsContainingWords(String word) {
        List<Tweet> tweetsByWord = jpaTweetRepository.findAllByContentIsContaining(word);
        return tweetsByWord.stream().map(tweet ->
                TweetMapper.mapToTweetDtoOut(tweet)
        ).collect(Collectors.toList());
    }

}
    @Override
    @Transactional
    public TweetDtoOut addTweetLike(long id){
        final Optional<Tweet> opTweet = jpaTweetRepository.findById(id);
        Tweet tweet = opTweet.get();
        if (opTweet.isPresent()){
            int likes = tweet.getLikes();
            likes++;
            tweet.setLikes(likes);
            jpaTweetRepository.save(tweet);
        }
        return TweetMapper.mapToTweetDtoOut(tweet);
    }

}