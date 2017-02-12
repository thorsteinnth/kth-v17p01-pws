package storage;

import bean.Edge;
import bean.Flight;
import bean.Node;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.ArrayList;

public class FlightStore {

    private static ArrayList<Node> nodes;
    private static ArrayList<Flight> flights;
    private static DefaultDirectedGraph<Node, Edge> graph;
    private static KShortestPaths kShortestPaths;
    private static FlightStore instance = null;

    private FlightStore() {

        this.nodes = generateNodes();
        this.flights = generateFlights();
        this.graph = generateGraph();
        this.kShortestPaths = new KShortestPaths(this.graph, 10);
    }

    public static FlightStore getFlightStore()
    {
        if (instance == null)
            instance = new FlightStore();

        return instance;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public DefaultDirectedGraph<Node, Edge> getGraph() {
        return graph;
    }

    public KShortestPaths getkShortestPaths() {
        return kShortestPaths;
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
        ArrayList<Flight> flights = new ArrayList<>();

        // this generates the following flights:
        // Reykjavik -> Stockholm -> Tallinn -> Helsinki -> Riga -> Vilinius
        for (int i = 0; i < nodes.size()-1; i++)
        {
            Flight flight = new Flight();
            flight.setFlightNumber("FL" + Integer.toString(i+1));
            flight.setDeparture(nodes.get(i));
            flight.setDestination(nodes.get(i+1));
            flights.add(flight);
        }

        // Reykjavik -> Helsinki
        Flight rvkToHelsinki = new Flight();
        rvkToHelsinki.setFlightNumber("FL6");
        rvkToHelsinki.setDeparture(nodes.get(0));
        rvkToHelsinki.setDestination(nodes.get(3));
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
            graph.setEdgeWeight(edge, 1);   // TODO Set price as weight?
        }

        return graph;
    }
}
