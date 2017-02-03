package itinerary.service;

import itinerary.bean.Flight;
import itinerary.bean.Itinerary;
import itinerary.bean.Node;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService(serviceName = "Itinerary",
        portName = "ItineraryPort",
        targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class ItineraryService
{
    private ArrayList<Node> nodes;
    private ArrayList<Flight> flights;

    public ItineraryService()
    {
        this.nodes = generateNodes();
        this.flights = generateFlights();
    }

    @WebMethod
    public Itinerary findItinerary(Flight flight) {

        Itinerary it = new Itinerary();
        it.setFlights(this.flights);
        return it;
    }

    private ArrayList<Node> generateNodes()
    {
        ArrayList<Node> nodes = new ArrayList<>();

        Node node1 = new Node();
        node1.setName("Reykjavik");
        nodes.add(node1);

        Node node2 = new Node();
        node2.setName("Stockholm");
        nodes.add(node2);

        Node node3 = new Node();
        node3.setName("Tallinn");
        nodes.add(node3);

        Node node4 = new Node();
        node4.setName("Helsinki");
        nodes.add(node4);

        Node node5 = new Node();
        node5.setName("Riga");
        nodes.add(node5);

        Node node6 = new Node();
        node6.setName("Vilinius");
        nodes.add(node6);

        return nodes;
    }

    private ArrayList<Flight> generateFlights()
    {
        ArrayList<Flight> flights = new ArrayList<Flight>();

        for (int i = 0; i < this.nodes.size()-1; i++)
        {
            Flight flight = new Flight();
            flight.setDeparture(this.nodes.get(i));
            flight.setDestination(this.nodes.get(i+1));
            flights.add(flight);
        }

        return flights;
    }


}
