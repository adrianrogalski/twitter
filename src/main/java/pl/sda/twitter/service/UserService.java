package pl.sda.twitter.service;

import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.Login;
import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService{
    List<UserDtoOut> findAllUsersContainingWords(String word);


    //  Optional<User> getUser
}
