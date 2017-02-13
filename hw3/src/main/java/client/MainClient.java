package client;

import bean.Itinerary;
import bean.User;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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

        Response getItinerariesResponse = webTarget.path("/itineraries")
                .queryParam("departure", departure).queryParam("destination", destination).request()
                .get(Response.class);
        System.out.println("Response: " + getItinerariesResponse);

        try {
            GenericType<ArrayList<Itinerary>> genericTypeItineraries = new GenericType<ArrayList<Itinerary>>(){};
            ArrayList<Itinerary> itineraries = webTarget.path("/itineraries")
                    .queryParam("departure", departure).queryParam("destination", destination).request()
                    .get(genericTypeItineraries);

            System.out.println(itineraries.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
