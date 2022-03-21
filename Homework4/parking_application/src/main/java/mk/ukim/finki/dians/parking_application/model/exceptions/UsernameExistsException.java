package mk.ukim.finki.dians.parking_application.model.exceptions;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String username) {
        super(String.format("User With Username: %s Already Exists", username));
    }
}
