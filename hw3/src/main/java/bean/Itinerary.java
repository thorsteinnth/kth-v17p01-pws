package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Itinerary {

    private List<Flight> flights;
    private String date;  // date needs to be on the format yyyy-MM-dd
    private int totalPrice;
    private int numberOfAvailableTickets;

    @XmlElement
    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @XmlElement
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @XmlElement
    public int getNumberOfAvailableTickets() {
        return numberOfAvailableTickets;
    }

    public void setNumberOfAvailableTickets(int numberOfAvailableTickets) {
        this.numberOfAvailableTickets = numberOfAvailableTickets;
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "flights=" + flights +
                ", date='" + date + '\'' +
                ", totalPrice=" + totalPrice +
                ", numberOfAvailableTickets=" + numberOfAvailableTickets +
                '}';
    }
}
