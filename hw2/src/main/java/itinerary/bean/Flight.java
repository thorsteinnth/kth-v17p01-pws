package itinerary.bean;

public class Flight
{
    private Node departure;
    private Node destination;

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
