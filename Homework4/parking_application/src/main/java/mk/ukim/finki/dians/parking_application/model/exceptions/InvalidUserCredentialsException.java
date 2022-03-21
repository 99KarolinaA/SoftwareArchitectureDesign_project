package mk.ukim.finki.dians.parking_application.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid User Credentials");
    }
}
