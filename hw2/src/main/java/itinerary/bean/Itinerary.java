package itinerary.bean;

import shared.Flight;

import java.util.List;

public class Itinerary
{
    private List<Flight> flights;

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
