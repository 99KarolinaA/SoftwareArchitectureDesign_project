package mk.ukim.finki.dians.allparkings.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException{

    public ParkingNotFoundException(Long id) {
        super(String.format("Parking with id: %d was not found", id));
    }
}
