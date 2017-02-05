package ticket.bean;

import shared.Flight;
import java.math.BigDecimal;
import java.util.Date;

public class TicketContainer
{
    private Date date;
    private int price;
    private int numberOfAvailableTickets;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
