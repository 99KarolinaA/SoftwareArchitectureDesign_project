package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.AllParkings;
import mk.ukim.finki.dians.parking_application.model.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * MVC Controller that handles the user's requests which are
 * parking-related.
 * The controller is mapped to the "/parking" path.
 */
@Controller
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Locating a parking by using some of the options
     * mapped on "/parking/locate"
     *
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return html view of the locate page
     */
    @GetMapping("/locate")
    public String getLocatePage(Model model) {

        model.addAttribute("bodyContent", "locate");
        return "master-template";
    }

    /**
     * POST request sent on the locate page
     * and mapped on "/parking/result"
     *
     * @param city    the city where the user looks for a parking
     * @param address the address where the user looks for a parking
     * @param sort    the sort option the user chose
     * @param model   object from the Model class which makes
     *                parameters accessible to the view page
     * @return html view of the locate page with an error message
     * if an error has occurred or
     * html view of the results page if the locating was successful or
     * an adequate html view if there are no parkings found
     */
    @PostMapping("/result")
    public String searchParking(@RequestParam(required = false) String city,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) String sort,
                                Model model) {

        if (city.isEmpty() && address.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "You must fill at least one field in order to search by city/address");
            model.addAttribute("currentLocation", "");
            model.addAttribute("bodyContent", "locate");
            return "master-template";
        }
        if (city.isEmpty()) {
            city = "empty";
        }
        if (address.isEmpty()) {
            address = "empty";
        }
        AllParkings allParkings = restTemplate.getForObject("http://parking-service/rest/filter/" + city + "/" + address + "/" + sort, AllParkings.class);

        if (allParkings == null || allParkings.getParkingList().isEmpty()) {
            return "notfoundparking";
        }
        model.addAttribute("parking", allParkings.getParkingList());
        model.addAttribute("currentLocation", "");

        model.addAttribute("bodyContent", "results");
        return "master-template";
    }

    /**
     * GET method as a response to a request made on "/parking/locate"
     * and mapped on "/parking/current-location"
     *
     * @param coordinates latitude+longitude of the user's location
     * @param model       object from the Model class which makes
     *                    parameters accessible to the view page
     * @return html view of the results page if the locating was successful
     * or an adequate html view if there are no parkings found
     */
    @GetMapping("/currentlocation")
    public String getCurrentLocation(@RequestParam String coordinates, Model model) {

        if (coordinates.equals("")) {

            model.addAttribute("bodyContent", "locate");
            return "master-template";
        }

        String[] coords = coordinates.split(" ");
        double latitude = Double.parseDouble(coords[0]);
        double longitude = Double.parseDouble(coords[1]);
        AllParkings allParkings = restTemplate.getForObject("http://parking-service/rest/current/" + latitude + "/" + longitude, AllParkings.class);
        if (allParkings == null) {
            return "notfoundparking";
        }

        model.addAttribute("parking", allParkings.getParkingList());
        model.addAttribute("currentLocation", "Sorted by shortest distance");
        model.addAttribute("bodyContent", "results");
        return "master-template";
    }


    /**
     * Response to a GET request for the page with all of the parkings
     *
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return html view of the parking page
     */
    @GetMapping("/allparkings")
    public String getParkingPage(Model model) {
        AllParkings allParkings = restTemplate.getForObject("http://parking-service/rest/all", AllParkings.class);
        model.addAttribute("parking", allParkings.getParkingList());
        model.addAttribute("bodyContent", "allparkings");
        return "master-template";
    }

    /**
     * Response to a request for deleting a parking.
     * Only an admin is authorized to delete a parking.
     *
     * @param id the id of the parking we want to delete
     * @return redirect to the parking page
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteParking(@PathVariable Long id) {
        restTemplate.getForObject("http://parking-service/rest/delete/" + id, Parking.class);
        return "redirect:/parking/allparkings";
    }

    /**
     * Response to a request for editing a parking.
     * Only an admin is authorized to edit a parking.
     *
     * @param id the id of the parking we want to edit
     * @return html view of the add form
     */
    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editParkingPage(@PathVariable Long id, Model model) {

        Parking parking = restTemplate.getForObject("http://parking-service/rest/parking/" + id, Parking.class);

        if (parking != null) {

            model.addAttribute("parking", parking);
            model.addAttribute("bodyContent", "add-parking");
            return "master-template";
        }

        return "notfoundparking";
    }

    /**
     * Response to a request for adding a new parking.
     * Only an admin is authorized to add a parking.
     *
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return html view of the add form
     */
    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addParkingPage(Model model) {
        model.addAttribute("bodyContent", "add-parking");
        return "master-template";
    }

    /**
     * POST request to save a parking
     *
     * @param id        id of the parking if the parking
     *                  is being edited
     * @param name      name of the parking
     * @param city      city where the parking is located
     * @param address   the address of the parking
     * @param latitude  geographic north-south coordinate
     * @param longitude geographic east-west coordinate
     * @param rating    rating of the parking (if it's known)
     * @return redirect to the parking page
     */
    @PostMapping("/add")
    public String saveParking(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String city,
            @RequestParam String address,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String rating) {

        if (id == null) {
            id = -1L;
        }
        restTemplate.getForObject("http://parking-service/rest/save/" + id + "/" + name + "/" + city + "/" + address
                + "/" + latitude + "/" + longitude + "/" + rating, Parking.class);
        return "redirect:/parking/allparkings";
    }

}

