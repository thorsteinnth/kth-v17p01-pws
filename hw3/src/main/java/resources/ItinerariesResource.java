package resources;

import bean.Edge;
import bean.Flight;
import bean.Itinerary;
import bean.Node;
import exceptions.NoRouteFoundException;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import storage.FlightStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;

@Path("/itineraries")
public class ItinerariesResource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    public ItinerariesResource()
    {}

    public ItinerariesResource(UriInfo uriInfo, Request request)
    {
        this.uriInfo = uriInfo;
        this.request = request;
    }

    // TODO : Change this to use Query/Path/Form .. some sort of string parameter
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Response findItineraries(JAXBElement<Flight> flightJAXBElement) {

        Flight flight = flightJAXBElement.getValue();

        try {
            List<Itinerary> itineraries = findKShortestPaths(flight.getDeparture(), flight.getDestination());
            return Response.ok(itineraries).build();
        }
        catch (NoRouteFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    private List<Itinerary> findKShortestPaths(Node src, Node dest) throws NoRouteFoundException
    {
        // The implementation only searches by actual object reference.
        // Have to find the right nodes in the graph
        Node departureGraphNode = null;
        Node destinationGraphNode = null;

        DefaultDirectedGraph<Node, Edge> graph = FlightStore.getFlightStore().getGraph();

        for (Node graphNode : graph.vertexSet())
            if (graphNode.getName().equals(src.getName()))
                departureGraphNode = graphNode;

        for (Node graphNode : graph.vertexSet())
            if (graphNode.getName().equals(dest.getName()))
                destinationGraphNode = graphNode;

        if (departureGraphNode == null || destinationGraphNode == null)
            throw new NoRouteFoundException();

        List<GraphPath> paths =
                FlightStore.getFlightStore().getkShortestPaths().getPaths(departureGraphNode, destinationGraphNode);

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
}
