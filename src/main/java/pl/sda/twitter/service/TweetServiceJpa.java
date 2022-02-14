package pl.sda.twitter.service;

import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.mapper.TweetMapper;
import pl.sda.twitter.model.Hashtag;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaHashtagRepository;
import pl.sda.twitter.repository.JpaTweetRepository;
import pl.sda.twitter.util.HashtagExtractor;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetServiceJpa implements TweetService{
    private final JpaTweetRepository jpaTweetRepository;
    private final JpaHashtagRepository jpaHashtagRepository;

    public TweetServiceJpa(JpaTweetRepository jpaTweetRepository, JpaHashtagRepository jpaHashtagRepository) {
        this.jpaTweetRepository = jpaTweetRepository;
        this.jpaHashtagRepository = jpaHashtagRepository;
    }

    @Override
    @Transactional
    public Optional<Tweet> addNewTweet(User user, TweetDtoIn dto) {
        Tweet savedTweet = saveTweetIntoRepo(dto, -1);
        return Optional.ofNullable(savedTweet);
    }

    @Transactional
    public Tweet saveTweetIntoRepo(TweetDtoIn dto, long parentTweetId) {
        Tweet tweet = TweetMapper.mapToTweet(dto);
        List<String> strings = HashtagExtractor.extractHashtagStrings(tweet);
        for (String hashtagString : strings) {
            Hashtag hashtag = Hashtag.builder()
                    .tweet(tweet)
                    .label(hashtagString.toUpperCase(Locale.ROOT))
                    .build();
            jpaHashtagRepository.save(hashtag);
        }
        Tweet savedTweet = jpaTweetRepository.save(tweet);
        return savedTweet;
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
        Tweet commentTweet = saveTweetIntoRepo(tweetDtoIn, parentTweetId);
        return Optional.ofNullable(commentTweet);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    @Transactional
    public List<TweetDtoOut> findAllTweetsContainingWords(String word) {
        List<Tweet> tweetsByWord = jpaTweetRepository.findAllByContentIsContaining(word);
        return tweetsByWord.stream().map(tweet ->
                TweetMapper.mapToTweetDtoOut(tweet)
        ).collect(Collectors.toList());
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