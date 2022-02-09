package pl.sda.twitter.service;

import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.Login;
import pl.sda.twitter.dto.TweetCommentsPage;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.mapper.TweetMapper;
import pl.sda.twitter.mapper.UserMapper;
import pl.sda.twitter.model.Tweet;
import pl.sda.twitter.model.User;
import pl.sda.twitter.repository.JpaUserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceJpa implements UserService{
    private final JpaUserRepository jpaUserRepository;

    public UserServiceJpa(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    @Transactional
    public List<UserDtoOut> findAllUsersContainingWords(String word) {
        List<User> usersByWord = jpaUserRepository.findUsersByUsernameContaining(word);
        return usersByWord.stream().map(user ->
                UserMapper.mapToUserDtoOut(user)
        ).collect(Collectors.toList());
    }
}
