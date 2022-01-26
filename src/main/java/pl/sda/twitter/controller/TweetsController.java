package pl.sda.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.twitter.dto.TweetDto;
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

    // Karol test ...

    @PostMapping("/{id}")
    ResponseEntity<Tweet> add(@PathVariable long id, @RequestBody TweetDto dto) {
        Optional<User> byId = userRepository.findById(id);
        return ResponseEntity.ok(tweetService.add(byId.get(), dto).get());
    }

}
