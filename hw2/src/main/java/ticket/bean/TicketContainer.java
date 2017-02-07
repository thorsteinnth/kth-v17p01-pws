package ticket.bean;

import shared.Flight;
import java.math.BigDecimal;
import java.util.Date;

public class TicketContainer
{
    private String date; // date needs to be on the format yyyy-MM-dd
    private int price;
    private int numberOfAvailableTickets;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }

    public void setNumberOfAvailableTickets(int numberOfAvailableTickets) {
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }
}
