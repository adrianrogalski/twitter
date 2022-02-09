package pl.sda.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.twitter.dto.Login;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaUserRepository;
import pl.sda.twitter.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JpaUserRepository jpaUserRepository;

    public UserController(UserService userService, JpaUserRepository jpaUserRepository) {
        this.userService = userService;
        this.jpaUserRepository = jpaUserRepository;
    }

    @GetMapping("/search/user/{word}")
    public List<UserDtoOut> findUsersByWord(@PathVariable String word) {
        return userService.findAllUsersContainingWords(word);
    }


}
