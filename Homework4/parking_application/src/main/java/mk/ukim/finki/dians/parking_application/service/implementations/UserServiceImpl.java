package mk.ukim.finki.dians.parking_application.service.implementations;

import mk.ukim.finki.dians.parking_application.model.User;
import mk.ukim.finki.dians.parking_application.model.enumeration.Role;
import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.dians.parking_application.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.dians.parking_application.model.exceptions.UsernameExistsException;
import mk.ukim.finki.dians.parking_application.repository.UserRepository;
import mk.ukim.finki.dians.parking_application.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Class which implements the UserService interface and
 * must implement all the methods from the interface.
 * Two dependency injections - UserRepository and PasswordEncoder
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method has the logic used for user registration.
     * It checks whether an attribute is wrong throws an Exception
     * or creates an User object with a constructor with the same parameters
     * @param username         username of the user is registering
     * @param password         user's password
     * @param repeatedPassword second input of the password
     *                         for verification/confirmation
     * @param name             user's first name
     * @param surname          user's last name
     * @return object as a result from the userRepository.save(user) method
     */
    @Override
    public User register(String username, String password, String repeatedPassword, String name, String surname) {

        if(Stream.of(username, password, repeatedPassword, name,surname).anyMatch(str -> str == null || str.isEmpty())) {
            throw new InvalidArgumentsException();
        }
        if (!repeatedPassword.equals(password)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new UsernameExistsException(username);
        }

        User user = new User(username, passwordEncoder.encode(password), name, surname);

        return userRepository.save(user);
    }


    /**
     * Method from the UserDetailsService interface
     * Loads the user by the username given as a parameter
     * @param username a string, user's username
     * @return an exception or user with the username sent as a parameter
     * @throws Exception if the username isn't found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
