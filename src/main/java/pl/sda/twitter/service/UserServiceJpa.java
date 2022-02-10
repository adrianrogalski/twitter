package pl.sda.twitter.service;

import org.springframework.stereotype.Service;
import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.dto.UserLoginForm;
import pl.sda.twitter.mapper.UserMapper;
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

    @Override
    public Optional<UserDtoOut> UserLogin(UserLoginForm login) {
        Optional<User> userByUsername = jpaUserRepository.findUserByUsername(login.getUsername());
        if (userByUsername.isPresent()){
            User user = userByUsername.get();
            if (login.getPassword().equals(user.getPassword())){
                return Optional.of(UserMapper.mapToUserDtoOut(user));
            }
        }
        return Optional.empty();
    }
}
