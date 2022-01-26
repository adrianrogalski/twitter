package pl.sda.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.twitter.dto.TweetDto;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.User;
import pl.sda.twitter.service.TweetService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .name("Jan")
                .build();

        User user2 = User.builder()
                .name("Jan")
                .build();

        TweetDto tweetDto1 = TweetDto.builder()
                .content("Daaaaaaaaaaaaaaaaaaaastruj się przez Apple. lub. Zarejestruj się, używając numeru telefonu lub adresu e-mail. Rejestrując się, zgadzasz się na Warunki ...")
                .build();

        tweetService.add(user1, tweetDto1);

        TweetDto tweetDto2 = TweetDto.builder()
                .content("Daaaaaaaaaaaaaaaaaaaastruj się przez Apple. lub. Zarejestruj się, używając numeru telefonu lub adresu e-mail. Rejestrując się, zgadzasz się na Warunki ...")
                .build();

        tweetService.add(user1, tweetDto2);

        TweetDtoOut tweetDtoOut1 = new TweetDtoOut ("Moj post", LocalDateTime.now());

        System.out.println(tweetDtoOut1.getTime());

    }
}
