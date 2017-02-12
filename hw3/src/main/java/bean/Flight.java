package bean;

import javax.xml.bind.annotation.XmlElement;

public class Flight {

    private String flightNumber;
    private Node departure;
    private Node destination;

    @XmlElement
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @XmlElement
    public Node getDeparture()
    {
        return departure;
    }

    public void setDeparture(Node departure)
    {
        this.departure = departure;
    }

    @XmlElement
    public Node getDestination()
    {
        return destination;
    }

    public void setDestination(Node destination)
    {
        this.destination = destination;
    }
}
