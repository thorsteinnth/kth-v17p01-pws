package ticket.bean;

import itinerary.testclient.Flight;
import java.math.BigDecimal;
import java.util.Date;

public class TicketContainer {

    private Flight flight;
    private Date date;
    private BigDecimal price;
    private int numberOfAvailableTickets;

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }

    public void setNumberOfAvailableTickets(int numberOfAvailableTickets) {
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }
}
