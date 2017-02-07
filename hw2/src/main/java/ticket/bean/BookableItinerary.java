package ticket.bean;

import shared.Flight;
import shared.Itinerary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookableItinerary extends Itinerary{

    private String date;  // date needs to be on the format yyyy-MM-dd
    private int totalPrice;
    private int numberOfAvailableTickets;

    public  BookableItinerary() {}

    public BookableItinerary(List<Flight> flights, String date, int totalPrice, int numberOfAvailableTickets) {
        setFlights(flights);
        this.date = date;
        this.totalPrice = totalPrice;
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }

    public void setNumberOfAvailableTickets(int numberOfAvailableTickets) {
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }
}
