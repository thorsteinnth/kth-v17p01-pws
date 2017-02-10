package client;

import bean.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
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
        System.out.println("Testing hello resource...");
        String response = webTarget.path(path).request().get(String.class);
        System.out.println(response);
    }

    private void testGetUsers(String path) {
        System.out.println("Testing get users...");
        String response = webTarget.path(path).request().get(String.class);
        System.out.println(response);

        GenericType<ArrayList<User>> genericTypeUsers = new GenericType<ArrayList<User>>(){};
        ArrayList<User> users = webTarget.path(path).request().get(genericTypeUsers);
    }

    private void testCreateUser(String path) {
        String username = "newUser";
        String password = "pass123";
        MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("username", username);
        formData.add("password", password);

        System.out.println("Testing create user...");
        User user = webTarget.path(path).request().post(Entity.form(formData), User.class);
        System.out.println(user.toString());
    }

    private void testUpdateUser(String path) {

    }

    private void testDeleteUser(String path) {
        System.out.println("Testing delete user...");
        String userToDelete = "user2";
        String response = webTarget.path(path + userToDelete).request().delete(String.class);
        System.out.println(response);
    }

}
