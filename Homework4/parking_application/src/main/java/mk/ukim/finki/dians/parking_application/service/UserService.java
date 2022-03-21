package mk.ukim.finki.dians.parking_application.service;

import mk.ukim.finki.dians.parking_application.model.User;
import mk.ukim.finki.dians.parking_application.model.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface which contains abstract and public methods
 * about the User class.
 * The interface extends the UserDetailsService - also an interface
 */
public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname);

}