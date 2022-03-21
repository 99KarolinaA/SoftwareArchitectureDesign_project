package mk.ukim.finki.dians.parking_application.service.implementations;

import mk.ukim.finki.dians.parking_application.model.Parking;
import mk.ukim.finki.dians.parking_application.model.exceptions.ParkingNotFoundException;
import mk.ukim.finki.dians.parking_application.repository.ParkingRepository;
import mk.ukim.finki.dians.parking_application.service.ParkingService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class which implements the ParkingService interface and
 * must implement all the methods from the interface.
 * Dependency injection - ParkingRepository
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    /**
     * A method that has the logic to find all the parkings
     * using the ParkingRepository's method findAll()
     * @return a List object which contains all the parkings
     */
    @Override
    public List<Parking> findAll() {
        return this.parkingRepository.findAll();
    }

    /**
     * A method which is using the ParkingRepository's
     * method findById and has one argument
     * @param id the id of the parking we want to find
     * @return object of the Optional class which is or is not a parking
     *          with id as the parameter
     */
    @Override
    public Optional<Parking> findById(Long id) {
        return this.parkingRepository.findById(id);
    }

    /**
     * Method which deletes a parking using parkingRepostory.deleteById(id) method
     * @param id the id of the parking we want to delete
     */
    @Override
    public void deleteById(Long id) {
        this.parkingRepository.deleteById(id);
    }

    /**
     * A methods used for finding all parkings located
     * less than 3 km from our current location
     * @param currentLatitude north-south coordinate of the user's location
     * @param currentLongitude east-west coordinate of the user's location
     * @return list of parkings
     */
    public List<Parking> findByCurrentAddress(Double currentLatitude, Double currentLongitude) {
        return parkingRepository.findAll().stream()
                .filter(parking ->
                        haversineDistance(parking.getLatitude(), parking.getLongitude(), currentLatitude, currentLongitude)<3)
                .sorted(Comparator.comparing(parking ->
                        haversineDistance(parking.getLatitude(), parking.getLongitude(), currentLatitude, currentLongitude)))
                .collect(Collectors.toList());

    }

    /**
     * A method for calculating angular distance between
     * two points on the surface of a sphere, using Math class.
     * @param latitude1 latitude of the first parking's location
     * @param longitude1 longitude of the first parking's location
     * @param latitude2 latitude of the second parking's location
     * @param longitude2 longitude of the first parking's location
     * @return distance between two parkings
     */
    private static double haversineDistance(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        final double R = 6371.0088; // Earth's radius Km
        Double latitudeDistance = toRad(latitude2 - latitude1);
        Double longitudeDistance = toRad(longitude2 - longitude1);

        Double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2) +
                Math.cos(toRad(latitude1)) * Math.cos(toRad(latitude2)) *
                        Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        Double distance = R * c; //distance in kilometers
        return distance;
    }

    /**
     * Conversion from degrees to radians using math function
     * @param value
     * @return the value in radians
     */
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    /**
     * Method which filters the parkings by specific criteria
     * @param city the city where we search
     * @param address the address of the parking(s)
     * @param sort show some specific results first
     *             (ex. by an alphabetical order)
     * @return list of parkings
     */
    @Override
    public List<Parking> findAllByCityOrAndAddressSorted(String city, String address, String sort) {

        List<Parking> parkingResults = null;
        if(sort==null){
            sort="name";
        }

        if (!city.isEmpty() && !address.isEmpty()) {

            parkingResults = sort.equals("name")
                    ? parkingRepository.findAllByAddressIgnoreCaseContainsAndCityIgnoreCaseContainsOrderByName(address,city)
                    : parkingRepository.findAllByAddressIgnoreCaseContainsAndCityIgnoreCaseContainsOrderByRatingDesc(address,city);

        } else if (!city.isEmpty()) {

            parkingResults = sort.equals("name")
                    ? parkingRepository.findAllByCityIgnoreCaseContainsOrderByName(city)
                    : parkingRepository.findAllByCityIgnoreCaseContainsOrderByRatingDesc(city);

        } else if (!address.isEmpty()) {

            parkingResults = sort.equals("name")
                    ? parkingRepository.findAllByAddressIgnoreCaseContainsOrderByName(address)
                    : parkingRepository.findAllByAddressIgnoreCaseContainsOrderByRatingDesc(address);
        }

        return parkingResults;
    }

    /**
     * Creating a new parking
     * @param name name of the parking
     * @param city city where the parking is located
     * @param address address of the parking
     * @param latitude geographic north-south coordinate
     * @param longitude geographic east-west coordinate
     * @param rating rating of the parking (if it's known)
     * @return object of the Optional class which is a result from
     *         the ParkingRepository method save()
     */
    @Override
    public Optional<Parking> save(String name, String city, String address, Double latitude, Double longitude, String rating) {

        return Optional.of(this.parkingRepository.save(new Parking(name, city, address, latitude, longitude, rating)));
    }

    /**
     * Editing an existing parking
     * @param id id of the parking edited
     * @param name name of the parking
     * @param city city where the parking is located
     * @param address address of the parking
     * @param latitude geographic north-south coordinate
     * @param longitude geographic east-west coordinate
     * @param rating rating of the parking
     * @return object of the Optional class which is a result from
     *         the ParkingRepository method save()
     */
    @Override
    public Optional<Parking> edit(Long id, String name, String city, String address, Double latitude, Double longitude, String rating) {

        Parking parking = this.parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));

        parking.setName(name);
        parking.setCity(city);
        parking.setAddress(address);
        parking.setLatitude(latitude);
        parking.setLongitude(longitude);
        parking.setRating(rating);

        return Optional.of(this.parkingRepository.save(parking));
    }

}
