package pl.sda.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaUserRepository;
import pl.sda.twitter.service.HashtagService;
import pl.sda.twitter.service.TweetService;
import pl.sda.twitter.util.HashtagExtractor;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootApplication
public class TwitterApplication implements CommandLineRunner {
    private final TweetService tweetService;
    private final JpaUserRepository userRepository;
    private final HashtagService hashtagService;

    public TwitterApplication(TweetService tweetService, JpaUserRepository userRepository, HashtagService hashtagService) {
        this.tweetService = tweetService;
        this.userRepository = userRepository;
        this.hashtagService = hashtagService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .name("Jan")
                .surname("Jumbo")
                .username("jan")
                .password("$2a$12$xyKIti7SOmJngrj3ZpcsKusZ4MF5G3/K0miPTX6isJj1rn9uFyGVy")
                .build();

        User savedUser1 = userRepository.save(user1);
        System.out.println("Utworzono usera o id: " + savedUser1.getId() + " oraz o nicku: " + user1.getUsername());

        User user2 = User.builder()
                .name("Damian")
                .id(2)
                .surname("Damiano")
                .username("damian1234")
                .password("$2a$12$xyKIti7SOmJngrj3ZpcsKusZ4MF5G3/K0miPTX6isJj1rn9uFyGVy")
                .build();

        User savedUser2 = userRepository.save(user2);
        System.out.println("Utworzono usera o id: " + savedUser2.getId() + " oraz o nicku: " + user2.getUsername());

        TweetDtoIn tweetDtoIn1 = TweetDtoIn.builder()
                .content("Daaaaaaaaaaaaaaaaaaaastruj się przez Apple. lub. Zarejestruj się, używając numeru telefonu lub adresu e-mail. Rejestrując się, zgadzasz się na Warunki ...")
                .author(savedUser1.getId())
                .build();

        Optional<Tweet> tweet1 = tweetService.addNewTweet(user1, tweetDtoIn1);
        System.out.println("Utworzono tweeta o id: " + tweet1.get().getId());

        TweetDtoIn tweetDtoIn2 = TweetDtoIn.builder()
                .content("Daaaaaaaaaaaaaaaaaaaastruj się przez Apple. lub. Zarejestruj się, używając numeru telefonu lub adresu e-mail. Rejestrując się, zgadzasz się na Warunki ...")
                .build();

        Optional<Tweet> tweet2 = tweetService.addNewTweet(user1, tweetDtoIn2);
        System.out.println("Utworzono tweeta o id: " + tweet2.get().getId());

        TweetDtoIn tweetDtoIn3 = TweetDtoIn.builder()
                .content("Zrobilem wyszukiwarke tweetow ktora dziala")
                .build();

        Optional<Tweet> tweet3 = tweetService.addNewTweet(user1, tweetDtoIn3);
        System.out.println("Utworzono tweeta o id: " + tweet3.get().getId());

        TweetDtoIn tweetDtoIn4 = TweetDtoIn.builder()
                .content("Ale jeszcze nie dziala tak jak powinna")
                .build();

        Optional<Tweet> tweet4 = tweetService.addNewTweet(user1, tweetDtoIn4);
        System.out.println("Utworzono tweeta o id: " + tweet4.get().getId());

        TweetDtoIn tweetDtoIn5 = TweetDtoIn.builder()
                .content("Powinna zadzialac po poprawkach")
                .build();

        Optional<Tweet> tweet5 = tweetService.addNewTweet(user2, tweetDtoIn5);
        System.out.println("Utworzono tweeta o id: " + tweet5.get().getId());

        TweetDtoIn tweetDtoIn6 = TweetDtoIn.builder()
                .content("damian1234 sdansidasidbisbciscsdicixcis")
                .build();

        Optional<Tweet> tweet6 = tweetService.addNewTweet(user1, tweetDtoIn6);
        System.out.println("Utworzono tweeta o id: " + tweet6.get().getId());

        TweetDtoIn tweetDtoIn7 = TweetDtoIn.builder()
                .content("#jobs")
                .build();

        Optional<Tweet> tweet7 = tweetService.addNewTweet(user2, tweetDtoIn7);
        System.out.println("Utworzono tweeta o id: " + tweet7.get().getId());

        TweetDtoIn tweetDtoIn8 = TweetDtoIn.builder()
                .content("#musk sdkasfjd #hello")
                .build();

        Optional<Tweet> tweet8 = tweetService.addNewTweet(user1, tweetDtoIn8);
        System.out.println("Utworzono tweeta o id: " + tweet8.get().getId());

        System.out.println("test wyciagania hashtagow z tweeta nr.8: ");
        HashtagExtractor.extractHashtagStrings(tweet8.get()).forEach(System.out::println);
        hashtagService.findAll().forEach(System.out::println);
        System.out.println("Popular");
        hashtagService.getPopularHashtags().forEach(System.out::println);

        tweetService.addBookmark(user1,tweetDtoIn1);
        System.out.println("Dodano zakładke");

        tweetService.addComment(1,tweetDtoIn2);
        tweetService.addComment(1,tweetDtoIn2);


        System.out.println(tweetService.getTweetComments(1));
    }
}
