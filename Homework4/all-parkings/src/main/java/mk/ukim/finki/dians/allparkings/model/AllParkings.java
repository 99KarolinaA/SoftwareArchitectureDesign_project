package mk.ukim.finki.dians.allparkings.model;

import java.util.List;

/**
 * Class that contains list of parking entities
 * the class constructor and/or setters/getters.
 */
public class AllParkings {
    private List<Parking> parkingList;

    public AllParkings(List<Parking> parkingList) {
        this.parkingList = parkingList;
    }

    public List<Parking> getParkingList() {
        return parkingList;
    }

    public void setParkingList(List<Parking> parkingList) {
        this.parkingList = parkingList;
    }
    public AllParkings(){

    }
}
