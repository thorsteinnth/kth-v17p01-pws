package client;

import bean.*;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

public class MainClient {

    private static String baseURL = "http://localhost:8080";

    private Client webClient;
    private WebTarget webTarget;
    private HttpAuthenticationFeature feature;

    public static void main(String[] args) {

        MainClient mainClient = new MainClient();
        mainClient.runScenario();
    }

    public MainClient() {
        this.webClient = ClientBuilder.newClient();
        this.feature = HttpAuthenticationFeature.basic("user8", "user8pass");
        this.webClient.register(feature);
        this.webTarget = webClient.target(baseURL);
    }

    private void runScenario() {
        System.out.println("Running main client scenario...");
        System.out.println("Log in user:");

        Response loginResponse = webTarget.path("/login").request().get(Response.class);
        System.out.println("Response: " + loginResponse);

        User loggedInUser = new User();

        try {
            loggedInUser = webTarget.path("/login").request().get(User.class);
            System.out.println("User logged in: " + loggedInUser.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }


        System.out.println("Get itineraries from Reykjavik to Tallinn:");

        String departure = "Reykjavik";
        String destination = "Tallinn";
        String date = "2017-02-13";

        Response getItinerariesResponse = webTarget.path("/itineraries")
                .queryParam("departure", departure)
                .queryParam("destination", destination)
                .queryParam("date", date)
                .request()
                .get(Response.class);
        System.out.println("Response: " + getItinerariesResponse);

        ArrayList<Itinerary> itineraries = new ArrayList<>();

        try {
            GenericType<ArrayList<Itinerary>> genericTypeItineraries = new GenericType<ArrayList<Itinerary>>(){};
            itineraries = webTarget.path("/itineraries")
                    .queryParam("departure", departure)
                    .queryParam("destination", destination)
                    .queryParam("date", date)
                    .request()
                    .get(genericTypeItineraries);

            System.out.println(itineraries.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Booking the itinerary with the most number of flights");
        // Let's just book the itinerary with the most number of flights, we love flying
        Itinerary selectedItinerary = null;
        int maxNumberOfFlights = -1;
        for (Itinerary itinerary : itineraries)
        {
            if (itinerary.getFlights().size() > maxNumberOfFlights)
            {
                maxNumberOfFlights = itinerary.getFlights().size();
                selectedItinerary = itinerary;
            }
        }

        if (selectedItinerary == null)
        {
            System.out.println("No suitable itinerary found, aborting booking ...");
            return;
        }

        // Create payment info
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardHolder("Donald Trump");
        paymentInfo.setCreditCardNumber("1234 5678 9999 9999");

        // Create the booking request container object
        BookingRequest request = new BookingRequest();
        request.setItinerary(selectedItinerary);
        request.setPaymentInfo(paymentInfo);

        // Make two calls, one that gets the response object and one that returns the list of tickets

        // Get the response object
        Response response = webTarget.path("/booking")
                .request()
                .post(Entity.xml(request), Response.class);
        System.out.println("Response: " + response);

        try
        {
            // Get the list of tickets straight away
            ArrayList<Ticket> tickets = webTarget.path("/booking")
                    .request()
                    .post(Entity.xml(request), new GenericType<ArrayList<Ticket>>(){});

            System.out.println(tickets.toString());
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
