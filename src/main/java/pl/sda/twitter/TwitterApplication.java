package pl.sda.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.twitter.dto.TweetDto;
import pl.sda.twitter.model.User;
import pl.sda.twitter.service.TweetService;

import java.util.Collections;

@SpringBootApplication
public class TwitterApplication implements CommandLineRunner {
    private final TweetService tweetService;

    public TwitterApplication(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .name("Jann")
                .build();

        TweetDto tweetDto1 = TweetDto.builder()
                .content("jdhasdfkjhsadfjhsdal")
                .build();

        tweetService.add(user1, tweetDto1);
    }
}
