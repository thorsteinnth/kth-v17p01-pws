package itinerary.testclient;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class ItineraryTestClient
{
    final QName qName = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "Itinerary");

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Specify the URL of the Itinerary Web Service");
            System.exit(-1);
        }

        URL url = getWSDLURL(args[0]);
        ItineraryTestClient client = new ItineraryTestClient();
        client.findItinerary(url);
    }

    private static URL getWSDLURL(String urlStr)
    {
        URL url;

        try
        {
            url = new URL(urlStr);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return url;
    }

    public void findItinerary(URL url)
    {
        Itinerary_Service itineraryService = new Itinerary_Service(url, qName);
        System.out.println("Service is " + itineraryService);
        ItineraryService port = itineraryService.getItineraryPort();

        Node departure1 = new Node();
        departure1.setName("Reykjavik");
        Node destination1 = new Node();
        destination1.setName("Tallinn");

        Flight flight1 = new Flight();
        flight1.setDeparture(departure1);
        flight1.setDestination(destination1);

        try
        {
            System.out.println("Requesting intinerary from " + departure1.getName() + " to " + destination1.getName());
            Itinerary itinerary1 = port.findItinerary(flight1);
            System.out.println(printItinerary(itinerary1));
        }
        catch (NoRouteFoundException_Exception ex)
        {
            System.out.println(ex);
        }

        Node departure2 = new Node();
        departure2.setName("Reykjavik");
        Node destination2 = new Node();
        destination2.setName("Riga");

        Flight flight2 = new Flight();
        flight2.setDeparture(departure2);
        flight2.setDestination(destination2);

        try
        {
            System.out.println("Requesting intinerary from " + departure2.getName() + " to " + destination2.getName());
            Itinerary itinerary2 = port.findItinerary(flight2);
            System.out.println(printItinerary(itinerary2));
        }
        catch (NoRouteFoundException_Exception ex)
        {
            System.out.println(ex);
        }

        Node departure3 = new Node();
        departure3.setName("Reykjavik");
        Node destination3 = new Node();
        destination3.setName("Tokyo");

        Flight flight3 = new Flight();
        flight3.setDeparture(departure3);
        flight3.setDestination(destination3);

        try
        {
            System.out.println("Requesting intinerary from " + departure3.getName() + " to " + destination3.getName());
            Itinerary itinerary3 = port.findItinerary(flight3);
            System.out.println(printItinerary(itinerary3));
        }
        catch (NoRouteFoundException_Exception ex)
        {
            System.out.println(ex);
        }
    }

    private String printItinerary(Itinerary itinerary)
    {
        StringBuilder sbFlights = new StringBuilder();
        for (Flight flight : itinerary.flights)
            sbFlights.append(printFlight(flight) + ",");

        return "Itinerary{" +
                "flights=" + sbFlights.toString() +
                '}';
    }

    private String printFlight(Flight flight)
    {
        return "Flight{" +
                "departure='" + flight.getDeparture().getName() + '\'' +
                ", destination='" + flight.getDestination().getName() + '\'' +
                '}';
    }
}
