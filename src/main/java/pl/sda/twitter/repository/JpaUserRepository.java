package pl.sda.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.twitter.model.User;

import java.util.List;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByUsernameContaining(String word);
}
