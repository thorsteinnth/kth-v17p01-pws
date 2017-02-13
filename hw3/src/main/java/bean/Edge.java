package bean;

import org.jgrapht.graph.DefaultWeightedEdge;

import javax.xml.bind.annotation.XmlElement;

public class Edge extends DefaultWeightedEdge{

    private Flight flight;

    @XmlElement
    public Flight getFlight()
    {
        return flight;
    }

    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }
}
