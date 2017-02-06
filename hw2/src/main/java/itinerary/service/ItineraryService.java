package itinerary.service;

import itinerary.bean.Edge;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import shared.SharedData;
import shared.Flight;
import shared.Itinerary;
import shared.Node;
import itinerary.exception.NoRouteFoundException;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedGraph;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "Itinerary",
        portName = "ItineraryPort",
        targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
@HandlerChain(file= "itinerary_handler.xml")
public class ItineraryService
{
    private ArrayList<Node> nodes;
    private ArrayList<Flight> flights;
    private DefaultDirectedGraph<Node, Edge> graph;
    private KShortestPaths kShortestPaths;

    public ItineraryService()
    {
        this.nodes = SharedData.getNodes();
        this.flights = SharedData.getFlights();
        this.graph = generateGraph();
        this.kShortestPaths = new KShortestPaths(this.graph, 10);
    }

    @WebMethod
    public List<Itinerary> findItineraries(Flight flight) throws NoRouteFoundException
    {
        return findKShortestPaths(flight.getDeparture(), flight.getDestination());
    }

    private List<Itinerary> findKShortestPaths(Node src, Node dest) throws NoRouteFoundException
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

        if (paths == null || paths.isEmpty())
            throw new NoRouteFoundException();

        List<Itinerary> itineraries = new ArrayList<>();

        for (GraphPath path : paths)
        {
            Itinerary itinerary = new Itinerary();
            List<Flight> pathFlightList = new ArrayList();

            for (Edge edge : (List<Edge>)path.getEdgeList())
                pathFlightList.add(edge.getFlight());

            itinerary.setFlights(pathFlightList);
            itineraries.add(itinerary);
        }

        return itineraries;
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
