package mk.ukim.finki.dians.allparkings.service;

import mk.ukim.finki.dians.allparkings.model.Parking;

import java.util.List;
import java.util.Optional;

/**
 * Interface which contains abstract and public methods
 * about the Parking class.
 */
public interface ParkingService

{

    List<Parking> findAll();

    Optional<Parking> findById(Long id);

    void deleteById(Long id);

    List<Parking> findAllByCityOrAndAddressSorted(String city, String address, String sort);

    List<Parking> findByCurrentAddress(Double currentlat, Double currentlng);

    Optional<Parking> save(String name, String city, String address, Double latitude, Double longitude, String rating);

    Optional<Parking> edit(Long id, String name, String city, String address, Double latitude, Double longitude, String rating);


}
