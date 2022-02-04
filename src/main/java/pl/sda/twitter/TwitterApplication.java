package pl.sda.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaUserRepository;
import pl.sda.twitter.service.TweetService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class TwitterApplication implements CommandLineRunner {
    private final TweetService tweetService;
    private final JpaUserRepository userRepository;

    public TwitterApplication(TweetService tweetService, JpaUserRepository userRepository) {
        this.tweetService = tweetService;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .name("Jan")
                .username("janek1234")
                .build();

        User savedUser1 = userRepository.save(user1);
        System.out.println("Utworzono usera o id: " + user1.getId() + " oraz o nicku: " + user1.getUsername());


        TweetDtoIn tweetDtoIn1 = TweetDtoIn.builder()
                .content("Daaaaaaaaaaaaaaaaaaaastruj się przez Apple. lub. Zarejestruj się, używając numeru telefonu lub adresu e-mail. Rejestrując się, zgadzasz się na Warunki ...")
                .build();

        Optional<Tweet> tweet1 = tweetService.addNewTweet(user1, tweetDtoIn1);
        System.out.println("Utworzono tweeta o id: " + tweet1.get().getId());

        TweetDtoIn tweetDtoIn2 = TweetDtoIn.builder()
                .content("Daaaaaaaaaaaaaaaaaaaastruj się przez Apple. lub. Zarejestruj się, używając numeru telefonu lub adresu e-mail. Rejestrując się, zgadzasz się na Warunki ...")
                .build();

        Optional<Tweet> tweet2 = tweetService.addNewTweet(user1, tweetDtoIn2);
        System.out.println("Utworzono tweeta o id: " + tweet2.get().getId());



    }
}
