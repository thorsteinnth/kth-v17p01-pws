package client;

import bean.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class TestClient {

    private static String baseURL = "http://localhost:8080";

    private Client webClient;
    private WebTarget webTarget;

    public static void main(String[] args) {

        TestClient testClient = new TestClient();
        testClient.testHelloResource("/hello");
        testClient.testGetUsers("/users");
        testClient.testCreateUser("/users");
        testClient.testGetUser("/users");
        testClient.testUpdateUser("/users");
        testClient.testDeleteUser("/users");
        testClient.testLogin("/users");

        testClient.testGetItineraries("/itineraries");
    }

    public TestClient() {
        this.webClient = ClientBuilder.newClient();
        this.webTarget = webClient.target(baseURL);
    }

    private void testHelloResource(String path) {
        System.out.println("-- Testing hello resource...");
        String response = webTarget.path(path).request().get(String.class);
        System.out.println(response);
    }

    private void testGetUsers(String path) {
        System.out.println("-- Testing get users...");
        Response response = webTarget.path(path).request().get(Response.class);
        System.out.println("Response: " + response);

        try {
            GenericType<ArrayList<User>> genericTypeUsers = new GenericType<ArrayList<User>>(){};
            ArrayList<User> users = webTarget.path(path).request().get(genericTypeUsers);
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
        String user2Id = "2";
        System.out.println();
        System.out.println("Testing get user with id=2...");
        Response response = webTarget.path(path + "/" + user2Id).request().get(Response.class);
        System.out.println("Response=" + response);

        try {
            User user = webTarget.path(path + "/" + user2Id).request().get(User.class);
            System.out.println(user.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        String user22Id = "22";
        System.out.println();
        System.out.println("Testing get user with id=22...");
        Response response2 = webTarget.path(path + "/" + user22Id).request().get(Response.class);
        System.out.println("Response=" + response2);

        try {
            User user = webTarget.path(path + "/" + user22Id).request().get(User.class);
            System.out.println(user.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void testUpdateUser(String path) {

        String userId = "2";
        User user = webTarget.path(path + "/" + userId).request().get(User.class);
        String newPassword = "newPassword123";
        user.setPassword(newPassword);

        System.out.println();
        System.out.println("Testing update user...");
        Response response = webTarget.path(path).request().put(Entity.xml(user), Response.class);
        System.out.println("Response: " + response);

        try {
            User updatedUser = webTarget.path(path).request().put(Entity.xml(user), User.class);
            System.out.println(updatedUser.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void testDeleteUser(String path) {
        System.out.println();
        System.out.println("Testing delete user...");
        String userIdToDelete = "2";

        Response response = webTarget.path(path + "/" + userIdToDelete).request().delete(Response.class);
        System.out.println("Response: " + response);
    }

    private void testLogin(String path) {
        System.out.println();
        System.out.println("Testing login...");
    }


    private void testGetItineraries(String path) {
        System.out.println();
        System.out.println("Testing get itineraries...");

        String departure = "Reykjavik";
        String destination = "Tallinn";

        Response response = webTarget.path(path)
                .queryParam("departure", departure).queryParam("destination", destination).request().get(Response.class);
        System.out.println("Response: " + response);

        try {

            GenericType<ArrayList<Itinerary>> genericTypeItineraries = new GenericType<ArrayList<Itinerary>>(){};
            ArrayList<Itinerary> itineraries = webTarget.path(path)
                    .queryParam("departure", departure).queryParam("destination", destination)
                    .request().get(genericTypeItineraries);

            System.out.println(itineraries.toString());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
