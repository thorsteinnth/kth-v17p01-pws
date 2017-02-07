package shared;

public class Flight
{
    private String flightNumber;
    private Node departure;
    private Node destination;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Node getDeparture()
    {
        return departure;
    }

    public void setDeparture(Node departure)
    {
        this.departure = departure;
    }

    public Node getDestination()
    {
        return destination;
    }

    public void setDestination(Node destination)
    {
        this.destination = destination;
    }
}
