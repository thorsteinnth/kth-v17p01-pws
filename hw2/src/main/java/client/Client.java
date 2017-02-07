package client;

import client.generated.authorization.Authorization;
import client.generated.authorization.AuthorizationService;
import client.generated.authorization.InvalidCredentials_Exception;
import client.generated.authorization.User;
import client.generated.itinerary.*;
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
    private final QName authorizationQName = new QName("http://hw2.flightticketreservation/authorization.service/authorization", "Authorization");
    private final QName itineraryQName = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "Itinerary");

    private AuthorizationService authorizationService;
    private ItineraryService itineraryService;

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("Parameter error: Specify the URL of the Authorization, Itinerary and Ticket web services");
            System.exit(-1);
        }

        URL authorizationUrl = getWSDLURL(args[0]);
        URL itineraryUrl = getWSDLURL(args[1]);

        new Client(authorizationUrl, itineraryUrl).run();
    }

    public Client(URL authorizationUrl, URL itineraryUrl)
    {
        Authorization authorization = new Authorization(authorizationUrl, authorizationQName);
        System.out.println("Authorization service is " + authorization);
        this.authorizationService = authorization.getAuthorizationPort();

        Itinerary_Service itineraryService = new Itinerary_Service(itineraryUrl, itineraryQName);
        System.out.println("Itinerary service is " + itineraryService);
        this.itineraryService = itineraryService.getItineraryPort();
    }

    private void run()
    {
        if (!getAuthorization())
            return;

        List<Itinerary> itineraries = findItineraries();
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
            System.out.println("Requesting intineraries from " + departure.getName() + " to " + destination.getName());
            List<Itinerary> itineraries = this.itineraryService.findItineraries(flight);
            System.out.println(printItineraries(itineraries));
            return itineraries;
        }
        catch (NoRouteFoundException_Exception|javax.xml.ws.WebServiceException ex)
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

    private String printItineraries(List<Itinerary> itineraries)
    {
        StringBuilder sb = new StringBuilder();

        for (Itinerary itinerary : itineraries)
        {
            sb.append(printItinerary(itinerary));
            sb.append("\n");
        }

        return sb.toString();
    }

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
                "departure='" + flight.getDeparture().getName() + '\'' +
                ", destination='" + flight.getDestination().getName() + '\'' +
                '}';
    }
}
