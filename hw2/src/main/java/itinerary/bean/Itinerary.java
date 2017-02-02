package itinerary.bean;

import java.util.ArrayList;

public class Itinerary
{
    private ArrayList<Flight> flights;

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }
}
