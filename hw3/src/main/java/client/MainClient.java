package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class MainClient {

    private static String baseURL = "http://localhost:8080/hello";

    private Client webClient;
    private WebTarget webTarget;

    public static void main(String[] args) {

        MainClient mainClient = new MainClient();
        mainClient.testHelloResource();
    }

    public MainClient() {
        this.webClient = ClientBuilder.newClient();
        this.webTarget = webClient.target(baseURL);
    }

    private void testHelloResource() {
        String response = webTarget.request().get(String.class);
        System.out.println("Testing hello resource..");
        System.out.println(response);
    }

}
