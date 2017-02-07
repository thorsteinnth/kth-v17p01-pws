package ticket.testclient;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TicketTestClient
{
    final QName qName = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "Ticket");

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Specify the URL of the Itinerary Web Service");
            System.exit(-1);
        }

        URL url = getWSDLURL(args[0]);
        TicketTestClient client = new TicketTestClient();
        client.ping(url);
        client.getPriceAndAvailabilityOfItineraries(url);
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

    public void ping(URL url)
    {
        Ticket_Service ticketService = new Ticket_Service(url, qName);
        System.out.println("Service is " + ticketService);
        TicketService port = ticketService.getTicketPort();

        try
        {
            System.out.println(port.ping());
        }
        catch (javax.xml.ws.WebServiceException ex)
        {
            System.out.println(ex);
        }
    }

    public void getPriceAndAvailabilityOfItineraries(URL url) {

        // Create two test itineraries
        List<Itinerary> itineraries = new ArrayList<>();

        // Itinerary 1
        Itinerary itinerary1 = new Itinerary();

        Node departure1 = new Node();
        departure1.setName("Reykjavik");
        Node destination1 = new Node();
        destination1.setName("Stockholm");

        Flight flight1 = new Flight();
        flight1.setDeparture(departure1);
        flight1.setDestination(destination1);
        itinerary1.getFlights().add(flight1);

        Node departure2 = new Node();
        departure2.setName("Stockholm");
        Node destination2 = new Node();
        destination2.setName("Tallinn");

        Flight flight2 = new Flight();
        flight2.setDeparture(departure2);
        flight2.setDestination(destination2);
        itinerary1.getFlights().add(flight2);

        itineraries.add(itinerary1);

        // Itinerary 2
        Itinerary itinerary2 = new Itinerary();

        Node departure3 = new Node();
        departure3.setName("Reykjavik");
        Node destination3 = new Node();
        destination3.setName("Helsinki");

        Flight flight3 = new Flight();
        flight3.setDeparture(departure3);
        flight3.setDestination(destination3);
        itinerary2.getFlights().add(flight3);

        Node departure4 = new Node();
        departure4.setName("Helsinki");
        Node destination4 = new Node();
        destination4.setName("Riga");

        Flight flight4 = new Flight();
        flight4.setDeparture(departure4);
        flight4.setDestination(destination4);
        itinerary2.getFlights().add(flight4);

        itineraries.add(itinerary2);

        Ticket_Service ticketService = new Ticket_Service(url, qName);
        System.out.println("Service is " + ticketService);
        TicketService port = ticketService.getTicketPort();

        // Set request date for today
        String requestDate = "2017-02-07";

        List<BookableItinerary> bookableItineraries =
                port.getPriceAndAvailabilityOfItinerariesForDate(itineraries, requestDate);

        System.out.println("Bookable itineraries: " + bookableItineraries.toString());
    }
}
