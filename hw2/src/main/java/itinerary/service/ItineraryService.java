package itinerary.service;

import itinerary.bean.Flight;
import itinerary.bean.Itinerary;
import itinerary.bean.Node;
import org.jgrapht.graph.DefaultDirectedGraph;

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
    private DefaultDirectedGraph<Node, Flight> graph;

    public ItineraryService()
    {
        this.nodes = generateNodes();
        this.flights = generateFlights();
        this.graph = generateGraph();

        System.out.println(graph);
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

        // this generatest the following flights:
        // Reykjavik -> Stockholm -> Tallinn -> Helsinki -> Riga -> Vilinius
        for (int i = 0; i < this.nodes.size()-1; i++)
        {
            Flight flight = new Flight();
            flight.setDeparture(this.nodes.get(i));
            flight.setDestination(this.nodes.get(i+1));
            flights.add(flight);
        }

        // Reykjavik -> Helsinki
        Flight rvkToHelsinki = new Flight();
        rvkToHelsinki.setDeparture(this.nodes.get(0));
        rvkToHelsinki.setDestination(this.nodes.get(3));
        flights.add(rvkToHelsinki);

        return flights;
    }

    private DefaultDirectedGraph<Node, Flight> generateGraph()
    {
        DefaultDirectedGraph<Node, Flight> graph = new DefaultDirectedGraph<Node, Flight>(Flight.class);

        for (Node node : this.nodes)
            graph.addVertex(node);

        for (Flight flight : this.flights)
            graph.addEdge(flight.getDeparture(), flight.getDestination(), flight);

        return graph;
    }
}
