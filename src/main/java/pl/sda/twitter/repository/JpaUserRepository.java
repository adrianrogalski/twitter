package pl.sda.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.twitter.model.User;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
}
