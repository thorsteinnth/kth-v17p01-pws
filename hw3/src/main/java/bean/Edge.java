package bean;

import javax.xml.bind.annotation.XmlElement;

public class Edge {

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
