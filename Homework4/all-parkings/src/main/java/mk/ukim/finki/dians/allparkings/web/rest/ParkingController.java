package mk.ukim.finki.dians.allparkings.web.rest;

import mk.ukim.finki.dians.allparkings.model.AllParkings;
import mk.ukim.finki.dians.allparkings.model.Parking;
import mk.ukim.finki.dians.allparkings.service.ParkingService;
import org.springframework.web.bind.annotation.*;

/**
 * MVC Rest controller that handles the user's requests which are
 * parking-related.
 * The controller is mapped to the "/rest" path.
 * Dependency injections - ParkingService
 */
@RestController
@RequestMapping("/rest")
public class ParkingController {
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    /**
     *
     * @return list of all the parking entities
     */
    @RequestMapping("/all")
    public AllParkings getAll() {

        return new AllParkings(parkingService.findAll());

    }

    /**
     *
     * @param latitude latitude of the user's location
     * @param longitude longitude of the user's location
     * @return list of parking entities that are located
     * less than 3 km from the user's current location
     */
    @GetMapping(value = {"/current/{latitude}/{longitude}", "/current"})
    public AllParkings CurrentAddress(@PathVariable("latitude") Double latitude,
                                      @PathVariable("longitude") Double longitude) {
        return new AllParkings(parkingService.findByCurrentAddress(latitude, longitude));
    }

    /**
     *
     * @param city the city where the user looks for a parking
     * @param address the address where the user looks for a parking
     * @param sort the sort option the user chose
     * @return list of filtered parking entities
     */
    @GetMapping("/filter/{city}/{address}/{sort}")
    public AllParkings filterParking(@PathVariable String city,
                                     @PathVariable String address,
                                     @PathVariable String sort) {
        return new AllParkings(parkingService.findAllByCityOrAndAddressSorted(city, address, sort));
    }

    /**
     *
     * @param id the id of the parking we want to delete
     * @return the parking
     */
    @GetMapping("/delete/{id}")
    public Parking deleteParking(@PathVariable Long id) {
        Parking parking = parkingService.findById(id).get();
        parkingService.deleteById(id);
        return parking;
    }

    /**
     *
     * @param id the id of the parking we want to find
     * @return the parking
     */
    @GetMapping("/parking/{id}")
    public Parking findParking(@PathVariable Long id) {
        return parkingService.findById(id).get();
    }

    /**
     *
     * @param id id of the parking if the parking
     *  is being edited
     * @param name name of the parking
     * @param city city where the parking is located
     * @param address the address of the parking
     * @param latitude geographic north-south coordinate
     * @param longitude geographic east-west coordinate
     * @param rating rating of the parking (if it's known)
     * @return the parking
     */
    @GetMapping("/save/{id}/{name}/{city}/{address}/{latitude}/{longitude}/{rating}")
    public Parking saveParking(@PathVariable Long id,
                               @PathVariable String name,
                               @PathVariable String city,
                               @PathVariable String address,
                               @PathVariable Double latitude,
                               @PathVariable Double longitude,
                               @PathVariable String rating) {
        Parking parking;
        if (id != -1L) {
            parking = this.parkingService.edit(id, name, city, address, latitude, longitude, rating).get();
        } else {
            parking = this.parkingService.save(name, city, address, latitude, longitude, rating).get();
        }
        return parking;
    }

}
