package ticket.bean;

import shared.Flight;
import shared.Itinerary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookableItinerary extends Itinerary{

    private Date date;
    private int totalPrice;
    private int numberOfAvailableTickets;

    public  BookableItinerary() {}

    public BookableItinerary(List<Flight> flights, Date date, int totalPrice, int numberOfAvailableTickets) {
        setFlights(flights);
        this.date = date;
        this.totalPrice = totalPrice;
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }

    public Date getDate() {
        return date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }
}
