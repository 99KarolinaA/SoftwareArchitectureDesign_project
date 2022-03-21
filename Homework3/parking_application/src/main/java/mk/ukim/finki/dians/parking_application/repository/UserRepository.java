package mk.ukim.finki.dians.parking_application.repository;

import mk.ukim.finki.dians.parking_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository of the User Entity.
 * Provides methods for manipulation with the User class
 * without their implementation.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

}
