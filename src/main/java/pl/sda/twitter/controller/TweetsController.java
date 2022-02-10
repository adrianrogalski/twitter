package pl.sda.twitter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaUserRepository;
import pl.sda.twitter.service.TweetService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TweetsController {
    private final TweetService tweetService;
    private final JpaUserRepository userRepository;

    public TweetsController(TweetService tweetService, JpaUserRepository userRepository) {
        this.tweetService = tweetService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TweetDtoOut> findTweetsByUser(@PathVariable long id) {
        return tweetService.findAllTweets(id);
    }

    @PostMapping("/{id}")
    ResponseEntity<Tweet> add(@PathVariable long id, @RequestBody TweetDtoIn dto) {
        Optional<User> userById = userRepository.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.addNewTweet(userById.get(), dto).get());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<TweetCommentsPage> getComments(@PathVariable(name = "id") long parentTweetId) {
        return ResponseEntity.of(tweetService.getTweetComments(parentTweetId));
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<Tweet> addComment(@PathVariable(name = "id") long parentTweetId, @RequestBody TweetDtoIn dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.addComment(parentTweetId, dto).get());
    }

    @GetMapping("/search/tweet/{word}")
    public List<TweetDtoOut> findTweetsByWord(@PathVariable String word) {
        return tweetService.findAllTweetsContainingWords(word);
    }
    @PostMapping("/tweet/like/{id}")
    public ResponseEntity<TweetDtoOut> addTweetLike(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.addTweetLike(id));
    }



}
