package resources;

import bean.*;
import exceptions.NoRouteFoundException;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import storage.FlightStore;
import storage.TicketStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
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

    @GET
    @Produces({MediaType.APPLICATION_XML})
    // date needs to be on the format yyyy-MM-dd
    public Response findItineraries(
            @QueryParam("departure") String departure,
            @QueryParam("destination") String destination,
            @QueryParam("date") String date) {

        Node dep = new Node();
        dep.setName(departure);
        Node dest = new Node();
        dest.setName(destination);

        Flight flight = new Flight();
        flight.setDeparture(dep);
        flight.setDestination(dest);

        List<Itinerary> allItineraries = new ArrayList<>();
        List<Itinerary> bookableItineraries = new ArrayList<>();

        try {
            allItineraries = findKShortestPaths(flight.getDeparture(), flight.getDestination());
        }
        catch (NoRouteFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }

        for (Itinerary itinerary : allItineraries) {
            int totalPriceForItinerary = 0;
            int numberOfAvailableTickets = Integer.MAX_VALUE;

            for(Flight itineraryFlight : itinerary.getFlights()) {
                TicketContainer tc = TicketStore.getTicketStore().getTicketContainer(itineraryFlight.getFlightNumber());

                // check if the requested date is the same as for the flight ticket container
                if(tc != null && date.equals(tc.getDate())) {
                    totalPriceForItinerary += tc.getPrice();
                    numberOfAvailableTickets = Integer.min(numberOfAvailableTickets, tc.getNumberOfAvailableTickets());
                }
                else {
                    numberOfAvailableTickets = 0;
                    break;
                }
            }

            if(numberOfAvailableTickets > 0) {

                Itinerary bookableItinerary = new Itinerary();
                bookableItinerary.setDate(date);
                bookableItinerary.setFlights(itinerary.getFlights());
                bookableItinerary.setTotalPrice(totalPriceForItinerary);
                bookableItinerary.setNumberOfAvailableTickets(numberOfAvailableTickets);
                bookableItineraries.add(bookableItinerary);
            }
        }

        if(bookableItineraries.size() > 0) {
            GenericEntity<List<Itinerary>> genericEntityItineraryList =
                    new GenericEntity<List<Itinerary>>(bookableItineraries){};
            return Response.ok(genericEntityItineraryList).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
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
            throw new NoRouteFoundException("No route found");

        List<GraphPath> paths =
                FlightStore.getFlightStore().getkShortestPaths().getPaths(departureGraphNode, destinationGraphNode);

        if (paths == null || paths.isEmpty())
            throw new NoRouteFoundException("No route found");

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
