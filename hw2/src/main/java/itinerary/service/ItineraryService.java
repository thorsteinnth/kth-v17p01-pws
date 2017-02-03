package itinerary.service;

import itinerary.bean.Edge;
import itinerary.bean.Flight;
import itinerary.bean.Itinerary;
import itinerary.bean.Node;
import itinerary.exception.NoRouteFoundException;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "Itinerary",
        portName = "ItineraryPort",
        targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class ItineraryService
{
    private ArrayList<Node> nodes;
    private ArrayList<Flight> flights;
    private DefaultDirectedGraph<Node, Edge> graph;
    private DijkstraShortestPath dijkstra;

    public ItineraryService()
    {
        this.nodes = generateNodes();
        this.flights = generateFlights();
        this.graph = generateGraph();
        this.dijkstra = new DijkstraShortestPath(this.graph);
    }

    @WebMethod
    public Itinerary findItinerary(Flight flight) throws NoRouteFoundException
    {
        Itinerary it = new Itinerary();
        it.setFlights(findShortestPath(flight.getDeparture(), flight.getDestination()));
        return it;
    }

    private List<Flight> findShortestPath(Node src, Node dest) throws NoRouteFoundException
    {
        // The Dijkstra implementation only searches by actual object reference.
        // Have to find the right nodes in the graph
        Node departureGraphNode = null;
        Node destinationGraphNode = null;

        for (Node graphNode : this.graph.vertexSet())
            if (graphNode.getName().equals(src.getName()))
                departureGraphNode = graphNode;

        for (Node graphNode : this.graph.vertexSet())
            if (graphNode.getName().equals(dest.getName()))
                destinationGraphNode = graphNode;

        if (departureGraphNode == null || destinationGraphNode == null)
            throw new NoRouteFoundException();

        GraphPath path = this.dijkstra.getPath(departureGraphNode, destinationGraphNode);

        if (path == null)
            throw new NoRouteFoundException();

        List<Flight> shortestPathFlightList = new ArrayList();
        for (Edge edge : (List<Edge>)path.getEdgeList())
            shortestPathFlightList.add(edge.getFlight());

        return shortestPathFlightList;
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

        // this generates the following flights:
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

    private DefaultDirectedGraph<Node, Edge> generateGraph()
    {
        DefaultDirectedGraph<Node, Edge> graph = new DefaultDirectedGraph<Node, Edge>(Edge.class);

        for (Node node : this.nodes)
        {
            graph.addVertex(node);
        }

        for (Flight flight : this.flights)
        {
            // Have to wrap flights in Edge, subclass of DefaultWeightedEdge
            // Do not want to extend DefaultWeightedEdge in the Flight class,
            // then we would get DefaultWeightedEdge (from jgrapht) into our web service definition (xsd schema)
            Edge edge = new Edge();
            edge.setFlight(flight);
            graph.addEdge(flight.getDeparture(), flight.getDestination(), edge);
            graph.setEdgeWeight(edge, 1);
        }

        return graph;
    }
}
