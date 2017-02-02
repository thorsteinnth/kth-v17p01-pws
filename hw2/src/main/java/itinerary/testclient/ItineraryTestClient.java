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

        System.out.println("Finding intinerary from Reykjavik to Tallinn");
        Flight flight = new Flight();
        flight.setDeparture("Reykjavik");
        flight.setDestination("Tallinn");

        Itinerary itinerary = port.findItinerary(flight);
        System.out.println(printItinerary(itinerary));
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
                "departure='" + flight.getDeparture() + '\'' +
                ", destination='" + flight.getDestination() + '\'' +
                '}';
    }
}
