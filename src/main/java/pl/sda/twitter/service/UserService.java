package pl.sda.twitter.service;

import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.dto.UserLoginForm;

import java.util.List;
import java.util.Optional;


public interface UserService{
    List<UserDtoOut> findAllUsersContainingWords(String word);
    Optional<UserDtoOut> UserLogin(UserLoginForm login);


    //  Optional<User> getUser
}
