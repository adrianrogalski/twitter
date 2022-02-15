package pl.sda.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.dto.UserLoginForm;
import pl.sda.twitter.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search/user/{word}")
    public List<UserDtoOut> findUsersByWord(@PathVariable String word) {
        return userService.findAllUsersContainingWords(word);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDtoOut> loginUser(@RequestBody UserLoginForm login){
        Optional<UserDtoOut> userDtoOut = userService.UserLogin(login);
      return  ResponseEntity.of(userDtoOut);
    }

}
