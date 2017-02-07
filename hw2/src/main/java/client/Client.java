package client;

import client.generated.authorization.Authorization;
import client.generated.authorization.AuthorizationService;
import client.generated.authorization.InvalidCredentials_Exception;
import client.generated.authorization.User;
import client.generated.itinerary.*;
import client.generated.itinerary.Flight;
import client.generated.itinerary.Itinerary;
import client.generated.itinerary.Node;
import client.generated.ticket.*;
import shared.SharedData;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the real client that talks to all services.
 * */
public class Client
{
    // NOTE: Since we have are talking to all services within the same class here we end up with some duplicate classes
    // that we get from different web services, e.g. itinerary.Itinerary and ticket.Itinerary.
    // We just make it work, but this should be done differently.

    private final QName authorizationQName = new QName("http://hw2.flightticketreservation/authorization.service/authorization", "Authorization");
    private final QName itineraryQName = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "Itinerary");
    private final QName ticketQName = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "Ticket");

    private AuthorizationService authorizationService;
    private ItineraryService itineraryService;
    private TicketService ticketService;

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("Parameter error: Specify the URL of the Authorization, Itinerary and Ticket web services");
            System.exit(-1);
        }

        URL authorizationUrl = getWSDLURL(args[0]);
        URL itineraryUrl = getWSDLURL(args[1]);
        URL ticketUrl = getWSDLURL(args[2]);

        new Client(authorizationUrl, itineraryUrl, ticketUrl).run();
    }

    public Client(URL authorizationUrl, URL itineraryUrl, URL ticketUrl)
    {
        Authorization authorization = new Authorization(authorizationUrl, authorizationQName);
        System.out.println("Authorization service is " + authorization);
        this.authorizationService = authorization.getAuthorizationPort();

        Itinerary_Service itineraryService = new Itinerary_Service(itineraryUrl, itineraryQName);
        System.out.println("Itinerary service is " + itineraryService);
        this.itineraryService = itineraryService.getItineraryPort();

        Ticket_Service ticket = new Ticket_Service(ticketUrl, ticketQName);
        System.out.println("Ticket service is " + ticket);
        this.ticketService = ticket.getTicketPort();
    }

    private void run()
    {
        if (!getAuthorization())
            return;

        List<Itinerary> itineraries = findItineraries();

        if (itineraries.size() == 0)
        {
            System.out.println("No itineraries found");
            return;
        }

        System.out.println("Itineraries:");
        for (Itinerary itinerary : itineraries)
            System.out.println(printItinerary(itinerary));

        // Have to convert from itinerary.Itinerary to ticket.Itinerary
        List<client.generated.ticket.Itinerary> ticketItineraries = new ArrayList<>();
        for (Itinerary itinerary : itineraries)
            ticketItineraries.add(convertToTicketItinerary(itinerary));

        List<BookableItinerary> bookableItineraries = getPriceAndAvailabilityOfItineraries(ticketItineraries);

        if (bookableItineraries.size() == 0)
        {
            System.out.println("No bookable itineraries");
            return;
        }

        System.out.println("Bookable itineraries:");
        for (BookableItinerary bookableItinerary : bookableItineraries)
            System.out.println(printBookableItinerary(bookableItinerary));

        List<Ticket> bookedTickets = bookCheapestItinerary(bookableItineraries);

        System.out.println("Booked tickets:");
        for (Ticket ticket : bookedTickets)
            System.out.println(printTicket(ticket));
    }

    private boolean getAuthorization()
    {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass1");

        System.out.println("Requesting authorization for: " + user.getUsername() + " " + user.getPassword());

        try
        {
            User userResponse = this.authorizationService.authorizeUser(user);
            System.out.println("Got auth token: " + userResponse.getToken());
            SharedData.setAuthToken(userResponse.getToken());
            return true;
        }
        catch (InvalidCredentials_Exception ex)
        {
            System.out.println("Invalid credentials for " + user.getUsername());
            return false;
        }
    }

    private List<Itinerary> findItineraries()
    {
        Node departure = new Node();
        departure.setName("Reykjavik");
        Node destination = new Node();
        destination.setName("Riga");

        Flight flight = new Flight();
        flight.setDeparture(departure);
        flight.setDestination(destination);

        try
        {
            System.out.println("Requesting itineraries from " + departure.getName() + " to " + destination.getName());
            List<Itinerary> itineraries = this.itineraryService.findItineraries(flight);
            return itineraries;
        }
        catch (NoRouteFoundException_Exception|javax.xml.ws.WebServiceException ex)
        {
            System.out.println(ex);
            return new ArrayList<>();
        }
    }

    private List<BookableItinerary> getPriceAndAvailabilityOfItineraries(List<client.generated.ticket.Itinerary> itineraries)
    {
        // Set request date for today
        String requestDate = "2017-02-07";

        System.out.println("Getting bookable itineraries for date " + requestDate);

        try
        {
            return this.ticketService.getPriceAndAvailabilityOfItinerariesForDate(itineraries, requestDate);
        }
        catch (javax.xml.ws.WebServiceException ex)
        {
            System.out.println(ex);
            return new ArrayList<>();
        }
    }

    private List<Ticket> bookCheapestItinerary(List<BookableItinerary> bookableItineraries)
    {
        System.out.println("Booking cheapest itinerary");

        int cheapestPrice = Integer.MAX_VALUE;
        BookableItinerary cheapestBookableItinerary = bookableItineraries.get(0);

        // Find the cheapest bookable itinerary
        for (BookableItinerary bookableItinerary : bookableItineraries)
        {
            if (bookableItinerary.getTotalPrice() <= cheapestPrice)
            {
                cheapestPrice = bookableItinerary.getTotalPrice();
                cheapestBookableItinerary = bookableItinerary;
            }
        }

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardHolder("Donald Trump");
        paymentInfo.setCreditCardNumber("9999 9999 9999 9999");

        // Book the cheapest itinerary

        try
        {
            return this.ticketService.bookItinerary(cheapestBookableItinerary, paymentInfo);
        }
        catch (javax.xml.ws.WebServiceException ex)
        {
            System.out.println(ex);
            return new ArrayList<>();
        }
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

    //region Object converters
    // This is a hack really. Convert between classes when we have duplicate generated classes from each web service

    private client.generated.ticket.Itinerary convertToTicketItinerary(client.generated.itinerary.Itinerary itinerary)
    {
        client.generated.ticket.Itinerary ticketItinerary = new client.generated.ticket.Itinerary();

        for (Flight flight : itinerary.getFlights())
            ticketItinerary.getFlights().add(convertToTicketFlight(flight));

        return ticketItinerary;
    }

    private client.generated.ticket.Flight convertToTicketFlight(client.generated.itinerary.Flight flight)
    {
        client.generated.ticket.Flight ticketFlight = new client.generated.ticket.Flight();
        ticketFlight.setDeparture(convertToTicketNode(flight.getDeparture()));
        ticketFlight.setDestination(convertToTicketNode(flight.getDestination()));
        ticketFlight.setFlightNumber(flight.getFlightNumber());
        return ticketFlight;
    }

    private client.generated.ticket.Node convertToTicketNode(client.generated.itinerary.Node node)
    {
        client.generated.ticket.Node ticketNode = new client.generated.ticket.Node();
        ticketNode.setName(node.getName());
        return ticketNode;
    }

    //endregion

    //region Print functions

    private String printItinerary(Itinerary itinerary)
    {
        StringBuilder sbFlights = new StringBuilder();
        for (Flight flight : itinerary.getFlights())
            sbFlights.append(printFlight(flight) + ",");

        return "Itinerary{" +
                "flights=" + sbFlights.toString() +
                '}';
    }

    private String printFlight(Flight flight)
    {
        return "Flight{" +
                "flightNumber='" + flight.getFlightNumber() + '\'' +
                "departure='" + flight.getDeparture().getName() + '\'' +
                ", destination='" + flight.getDestination().getName() + '\'' +
                '}';
    }

    private String printBookableItinerary(BookableItinerary itinerary) {

        StringBuilder sbFlights = new StringBuilder();
        for (client.generated.ticket.Flight flight : itinerary.getFlights())
            sbFlights.append(printFlight(flight) + ",");

        return "BookableItinerary{" +
                "date='" + itinerary.getDate() + '\'' +
                ", totalPrice=" + itinerary.getTotalPrice() +
                ", numberOfAvailableTickets=" + itinerary.getNumberOfAvailableTickets() +
                ", flights=" + sbFlights.toString() +
                '}';
    }

    private String printFlight(client.generated.ticket.Flight flight) {
        return "Flight{" +
                "flightNumber='" + flight.getFlightNumber() + '\'' +
                ", departure='" + flight.getDeparture().getName() + '\'' +
                ", destination='" + flight.getDestination().getName() + '\'' +
                '}';
    }

    private String printTicket(Ticket ticket)
    {
        return "Ticket{" +
                "flight=" + printFlight(ticket.getFlight()) +
                ", date='" + ticket.getDate() + '\'' +
                ", isBooked=" + ticket.isBooked().toString() +
                ", isIssued=" + ticket.isIssued().toString() +
                ", paymentInfo=" + printPaymentInfo(ticket.getPaymentInfo()) +
                '}';
    }

    private String printPaymentInfo(PaymentInfo paymentInfo)
    {
        return "PaymentInfo{" +
                "cardHolder='" + paymentInfo.getCardHolder() + '\'' +
                ", creditCardNumber='" + paymentInfo.getCreditCardNumber() + '\'' +
                '}';
    }

    //endregion
}
