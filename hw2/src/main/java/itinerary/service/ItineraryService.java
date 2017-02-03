package itinerary.service;

import itinerary.bean.Edge;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import shared.SharedData;
import shared.Flight;
import itinerary.bean.Itinerary;
import shared.Node;
import itinerary.exception.NoRouteFoundException;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

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
    private KShortestPaths kShortestPaths;

    public ItineraryService()
    {
        this.nodes = SharedData.getNodes();
        this.flights = SharedData.getFlights();
        this.graph = generateGraph();
        this.dijkstra = new DijkstraShortestPath(this.graph);
        this.kShortestPaths = new KShortestPaths(this.graph, 10);
    }

    @WebMethod
    public Itinerary findItinerary(Flight flight) throws NoRouteFoundException
    {
        // TODO Return K shortest paths
        Itinerary it = new Itinerary();
        it.setFlights(dijkstraFindShortestPath(flight.getDeparture(), flight.getDestination()));
        findKShortestPaths(flight.getDeparture(), flight.getDestination());
        return it;
    }

    private List<Flight> dijkstraFindShortestPath(Node src, Node dest) throws NoRouteFoundException
    {
        // The implementation only searches by actual object reference.
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

    private void findKShortestPaths(Node src, Node dest) throws NoRouteFoundException
    {
        // The implementation only searches by actual object reference.
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

        List<GraphPath> paths = this.kShortestPaths.getPaths(departureGraphNode, destinationGraphNode);

        if (paths == null)
            throw new NoRouteFoundException();

        //List<Flight> shortestPathFlightList = new ArrayList();
        //for (Edge edge : (List<Edge>)path.getEdgeList())
        //    shortestPathFlightList.add(edge.getFlight());

        System.out.println("K shortest paths");
        System.out.println(paths);

        // TODO Return paths

        return;
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
