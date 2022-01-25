package pl.sda.twitter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.service.TweetService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TweetsController {
    private final TweetService tweetService;

    public TweetsController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/{id}")
    public List<Tweet> findTweetsByUser(@PathVariable long id) {
        return tweetService.findAllTweets(id);
    }
}
