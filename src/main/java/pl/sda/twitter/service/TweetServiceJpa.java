package pl.sda.twitter.service;

import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.TweetDto;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaTweetRepository;
import pl.sda.twitter.repository.JpaUserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceJpa implements TweetService{
    private final JpaTweetRepository jpaTweetRepository;
    private final JpaUserRepository jpaUserRepository;

    public TweetServiceJpa(JpaTweetRepository jpaTweetRepository, JpaUserRepository jpaUserRepository) {
        this.jpaTweetRepository = jpaTweetRepository;
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public Optional<Tweet> add(User user, TweetDto dto) {
        Tweet tweet = Tweet.builder()
                .author(user)
                .content(dto.getContent())
                .hashtagSet(Collections.emptySet())
                .publishingTime(now())
                .respondId(0)
                .build();
        Tweet savedTweet = jpaTweetRepository.save(tweet);
        return Optional.ofNullable(savedTweet);
    }

    @Override
    public List<Tweet> findAllTweets(long userId) {
        return jpaTweetRepository.findByAuthor_Id(userId);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}