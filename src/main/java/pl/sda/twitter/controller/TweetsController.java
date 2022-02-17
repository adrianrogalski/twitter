package pl.sda.twitter.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoIn;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaBookmarkRepository;
import pl.sda.twitter.repository.JpaUserRepository;
import pl.sda.twitter.service.TweetService;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TweetsController {
    private final TweetService tweetService;
    private final JpaUserRepository userRepository;
    private final JpaBookmarkRepository bookmarkRepository;


    public TweetsController(TweetService tweetService, JpaUserRepository userRepository, JpaBookmarkRepository bookmarkRepository) {
        this.tweetService = tweetService;
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @GetMapping("/{id}")
    public List<TweetDtoOut> findTweetsByUser(@PathVariable long id) {
        return tweetService.findAllTweets(id);
    }

    @GetMapping("/user/{username}")
    public List<TweetDtoOut> findTweetsByUsername(@PathVariable String username) {
        return tweetService.findAllTweetsByUsername(username);
    }

    @PostMapping("/new-tweet")
    ResponseEntity<Tweet> add(@AuthenticationPrincipal User user, @RequestBody TweetDtoIn dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.addNewTweet(user, dto).get());
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
    @ResponseStatus(HttpStatus.OK)
    public void addTweetLike(@AuthenticationPrincipal User user, @PathVariable long id) {
        tweetService.addTweetLike(user, id);
    }

    @GetMapping("image/{name}")
    public ResponseEntity showImage(@PathVariable String name) throws IOException {
        File file = new File("uploads/" + name);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(name)))
                .body(Files.readAllBytes(file.toPath()));
    }

    @PostMapping("/upload")
    @ResponseBody
    public String handleFile(@RequestPart(name = "fileupload") MultipartFile file) { // jako parametr metody przyjmujemy MultipartFile o nazwie fileupload (nazwa ta musi być taka sama jak nazwa pola w formularzu).
        File uploadDirectory = new File("uploads");
        uploadDirectory.mkdirs(); // upewniam się, że katalog, do którego chcę zapisać plik, istnieje, a jeśli nie, to go tworzę
        File oFile = new File("uploads/" + file.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(oFile);
             InputStream inputStream = file.getInputStream()) {
            IOUtils.copy(inputStream, os); // pobranie strumienia wejściowego z przesłanego pliku i przekopiowanie zawartość do stworzonego strumienia wyjsciowego
        } catch (IOException e) {
            e.printStackTrace();
            return "There was an error uploading the file: " + e.getMessage();
        }
        return "File uploaded!";
    }

    @DeleteMapping("/tweet/{id}")
    public void delete(@PathVariable long id){
        tweetService.deleteTweetById(id);
    }

    @PostMapping("/bookmark/{id}")
    ResponseEntity<Tweet> addBookmark(@PathVariable long id, @RequestBody TweetDtoIn dto) {
        Optional<User> userById = userRepository.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.addBookmark(userById.get(), dto).get());
    }
    @GetMapping("/bookmarklist")
    public List<Tweet> findBookmarks() {
        return tweetService.findAllBookmarks();
    }
}
