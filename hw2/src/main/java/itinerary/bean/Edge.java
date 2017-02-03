package itinerary.bean;

import org.jgrapht.graph.DefaultWeightedEdge;
import shared.Flight;

public class Edge extends DefaultWeightedEdge
{
    private Flight flight;

    public Flight getFlight()
    {
        return flight;
    }

    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }
}
