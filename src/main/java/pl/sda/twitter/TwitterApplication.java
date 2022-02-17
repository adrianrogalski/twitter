package pl.sda.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
                .surname("Konieczny")
                .username("jan")
                .password("$2a$12$xyKIti7SOmJngrj3ZpcsKusZ4MF5G3/K0miPTX6isJj1rn9uFyGVy")
//                .password("1234")
                .build();

        User savedUser1 = userRepository.save(user1);
        System.out.println("Utworzono usera o id: " + savedUser1.getId() + " oraz o nicku: " + user1.getUsername());

        User user2 = User.builder()
                .name("Damian")
                .id(2)
                .surname("Kowalski")
                .username("damian")
                .password("$2a$12$xyKIti7SOmJngrj3ZpcsKusZ4MF5G3/K0miPTX6isJj1rn9uFyGVy")
                .build();

        User savedUser2 = userRepository.save(user2);
        System.out.println("Utworzono usera o id: " + savedUser2.getId() + " oraz o nicku: " + user2.getUsername());

        TweetDtoIn tweetDtoIn1 = TweetDtoIn.builder()
                .content("Exciting time in commercial spaceflight. Polaris Program will undertake a series of tech demo missions culminating in first flight of Starship.")
                .author(savedUser1.getId())
                .build();

        Optional<Tweet> tweet1 = tweetService.addNewTweet(user1, tweetDtoIn1);
        System.out.println("Utworzono tweeta o id: " + tweet1.get().getId());

        TweetDtoIn tweetDtoIn2 = TweetDtoIn.builder()
                .content("The duty of a leader is to serve their people, not for the people to serve them")
                .build();

        Optional<Tweet> tweet2 = tweetService.addNewTweet(user1, tweetDtoIn2);
        System.out.println("Utworzono tweeta o id: " + tweet2.get().getId());

        TweetDtoIn tweetDtoIn3 = TweetDtoIn.builder()
                .content("The National Academy of Engineering is excited to announce the election of 111 new members and 22 international members. The newly elected members will be formally inducted at our annual meeting on Oct 2.")
                .build();

        Optional<Tweet> tweet3 = tweetService.addNewTweet(user1, tweetDtoIn3);
        System.out.println("Utworzono tweeta o id: " + tweet3.get().getId());

        TweetDtoIn tweetDtoIn4 = TweetDtoIn.builder()
                .content("POV: Jungkook stopped in the middle of walking just to see u standing there, ur 2nd last face emoji is ur reaction !")
                .build();

        Optional<Tweet> tweet4 = tweetService.addNewTweet(user1, tweetDtoIn4);
        System.out.println("Utworzono tweeta o id: " + tweet4.get().getId());

        TweetDtoIn tweetDtoIn5 = TweetDtoIn.builder()
                .content("Przyjemność ponad wszystko. Nowy, hybrydowy Peugeot 308 już w salonach. Sprawdź wyjątkowe oferty leasingowe. Tak wyjątkowe, jak Twój biznes.")
                .build();

        Optional<Tweet> tweet5 = tweetService.addNewTweet(user2, tweetDtoIn5);
        System.out.println("Utworzono tweeta o id: " + tweet5.get().getId());

        TweetDtoIn tweetDtoIn6 = TweetDtoIn.builder()
                .content("U.S. Birthrate Fell By 4% In 2020, Hitting Another Record LowFor the sixth year in a row, the number of U.S. births fell in 2020, reaching the lowest level since 1979. The fertility rate remains below ")
                .build();

        Optional<Tweet> tweet6 = tweetService.addNewTweet(user1, tweetDtoIn6);
        System.out.println("Utworzono tweeta o id: " + tweet6.get().getId());

        TweetDtoIn tweetDtoIn7 = TweetDtoIn.builder()
                .content("CEO, @dsn_software, @Cytek_Security, and @TechEvolutions | Revolutionizing Digital Healthcare | Transforming Cybersecurity #jobs")
                .build();

        Optional<Tweet> tweet7 = tweetService.addNewTweet(user2, tweetDtoIn7);
        System.out.println("Utworzono tweeta o id: " + tweet7.get().getId());

        TweetDtoIn tweetDtoIn8 = TweetDtoIn.builder()
                .content("#musk 1469 Starlink satellites active 272 moving to operational orbits Laser links activate soon #hello")
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

        System.out.println(tweetService.addTweetLike("jan", 1));
        System.out.println(tweetService.addTweetLike("jan", 2));
        System.out.println(tweetService.addTweetLike("jan", 3));



    }
}
