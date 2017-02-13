package client;

import bean.*;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

public class TestClient {

    private static String baseURL = "http://localhost:8080";

    private Client webClient;
    private WebTarget webTarget;
    private HttpAuthenticationFeature feature;

    public static void main(String[] args) {

        TestClient testClient = new TestClient();
        testClient.testHelloResource("/hello");
        testClient.testGetUser("/users");
        testClient.testGetUsers("/users");
        testClient.testCreateUser("/users");
        testClient.testUpdateUser("/users");
        testClient.testDeleteUser("/users");
        testClient.testLogin("/login");

        List<Itinerary> itineraries = testClient.testGetItineraries("/itineraries");
        if (itineraries.size() > 0)
            testClient.testBooking("/booking", itineraries);

        // Call get itineraries again to verify that tickets have been withdrawn
        testClient.testGetItineraries("/itineraries");
    }

    public TestClient() {
        this.webClient = ClientBuilder.newClient();
        this.feature = HttpAuthenticationFeature.basic("user0", "user0pass");
        this.webClient.register(this.feature);
        this.webTarget = webClient.target(baseURL);
    }

    private void testHelloResource(String path) {
        System.out.println();
        System.out.println("-- Testing hello resource...");
        String response = webTarget.path(path).request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                .get(String.class);
        System.out.println(response);
    }

    private void testGetUsers(String path) {
        System.out.println("-- Testing get users...");
        Response response = webTarget.path(path).request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                .get(Response.class);
        System.out.println("Response: " + response);

        try {
            GenericType<ArrayList<User>> genericTypeUsers = new GenericType<ArrayList<User>>(){};
            ArrayList<User> users = webTarget.path(path).request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                    .get(genericTypeUsers);

            for (User user : users) {
                System.out.println(user.toString());
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void testCreateUser(String path) {
        String username = "newUser4";
        String password = "pass123";
        MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("username", username);
        formData.add("password", password);

        System.out.println();
        System.out.println("Testing create user...");
        Response response = webTarget.path(path).request().post(Entity.form(formData), Response.class);
        System.out.println("Response: " + response);

        try {
            User user = webTarget.path(path).request().post(Entity.form(formData), User.class);
            System.out.println(user.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void testGetUser(String path) {
        String user0Id = "0";
        System.out.println();
        System.out.println("Testing get user with id=0...");
        Response response = webTarget.path(path + "/" + user0Id).request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                .get(Response.class);
        System.out.println("Response=" + response);

        try {
            User user = webTarget.path(path + "/" + user0Id).request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                    .get(User.class);
            System.out.println(user.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        String user22Id = "22";
        System.out.println();
        System.out.println("Testing get user with id=22...");
        Response response2 = webTarget.path(path + "/" + user22Id).request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                .get(Response.class);
        System.out.println("Response=" + response2);

        try {
            User user = webTarget.path(path + "/" + user22Id).request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user0pass")
                    .get(User.class);
            System.out.println(user.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void testUpdateUser(String path) {

        String userId = "0";
        User user = webTarget.path(path + "/" + userId).request().get(User.class);
        String newPassword = "newPassword123";
        user.setPassword(newPassword);

        System.out.println();
        System.out.println("Testing update user...");
        Response response = webTarget.path(path).request().put(Entity.xml(user), Response.class);
        System.out.println("Response: " + response);

        try {
            User updatedUser = webTarget.path(path).request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "newPassword123")
                    .put(Entity.xml(user), User.class);
            System.out.println(updatedUser.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void testDeleteUser(String path) {
        System.out.println();
        System.out.println("Testing delete user...");
        String userIdToDelete = "0";

        Response response = webTarget.path(path + "/" + userIdToDelete).request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user0")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "newPassword123")
                .delete(Response.class);
        System.out.println("Response: " + response);
    }

    private void testLogin(String path) {
        System.out.println();
        System.out.println("Testing login...");

        Response response = webTarget.path(path).request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user1")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user1pass")
                .get(Response.class);
        System.out.println("Response: " + response);

        try {
            User loggedInUser = webTarget.path(path).request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user1")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user1pass")
                    .get(User.class);
            System.out.println("Logged in user: " + loggedInUser.toString());
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }


    private List<Itinerary> testGetItineraries(String path)
    {
        System.out.println();
        System.out.println("Testing get itineraries...");

        String departure = "Reykjavik";
        String destination = "Riga";
        String date = "2017-02-13";

        Response response = webTarget.path(path)
                .queryParam("departure", departure)
                .queryParam("destination", destination)
                .queryParam("date", date)
                .request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user1")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user1pass")
                .get(Response.class);
        System.out.println("Response: " + response);

        try
        {
            GenericType<ArrayList<Itinerary>> genericTypeItineraries = new GenericType<ArrayList<Itinerary>>(){};
            ArrayList<Itinerary> itineraries = webTarget.path(path)
                    .queryParam("departure", departure)
                    .queryParam("destination", destination)
                    .queryParam("date", date)
                    .request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user1")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user1pass")
                    .get(genericTypeItineraries);

            System.out.println(itineraries.toString());
            return itineraries;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return new ArrayList<>();
        }
    }

    private void testBooking(String path, List<Itinerary> itineraries)
    {
        System.out.println();
        System.out.println("Testing booking ...");

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
        Response response = webTarget.path(path)
                .request()
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user1")
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user1pass")
                .post(Entity.xml(request), Response.class);
        System.out.println("Response: " + response);

        try
        {
            // Get the list of tickets straight away
            ArrayList<Ticket> tickets = webTarget.path(path)
                    .request()
                    .property(HTTP_AUTHENTICATION_BASIC_USERNAME, "user1")
                    .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "user1pass")
                    .post(Entity.xml(request), new GenericType<ArrayList<Ticket>>(){});

            System.out.println(tickets.toString());
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
