package ticket.bean;

import shared.Flight;
import shared.Itinerary;

import java.math.BigDecimal;
import java.util.List;

public class BookableItinerary extends Itinerary{

    private int totalPrice;
    private int numberOfAvailableTickets;

    public  BookableItinerary() {}

    public BookableItinerary(List<Flight> flights, int totalPrice, int numberOfAvailableTickets) {
        setFlights(flights);
        this.totalPrice = totalPrice;
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }
}
