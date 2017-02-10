package client;

import bean.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;

public class MainClient {

    private static String baseURL = "http://localhost:8080";

    private Client webClient;
    private WebTarget webTarget;

    public static void main(String[] args) {

        MainClient mainClient = new MainClient();
        mainClient.testHelloResource("/hello");
        mainClient.testGetUsers("/users");
    }

    public MainClient() {
        this.webClient = ClientBuilder.newClient();
        this.webTarget = webClient.target(baseURL);
    }

    private void testHelloResource(String path) {
        String response = webTarget.path(path).request().get(String.class);
        System.out.println("Testing hello resource..");
        System.out.println(response);
    }

    private void testGetUsers(String path) {
        String response = webTarget.path(path).request().get(String.class);
        System.out.println("Testing get users..");
        System.out.println(response);

        GenericType<ArrayList<User>> genericTypeUsers = new GenericType<ArrayList<User>>(){};
        ArrayList<User> users = webTarget.path(path).request().get(genericTypeUsers);
    }

}
