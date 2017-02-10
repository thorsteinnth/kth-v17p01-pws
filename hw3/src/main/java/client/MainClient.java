package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class MainClient {

    public static void main(String[] args) {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/hello");
        String response = webTarget.request().get(String.class);
        System.out.println(response);
    }
}
