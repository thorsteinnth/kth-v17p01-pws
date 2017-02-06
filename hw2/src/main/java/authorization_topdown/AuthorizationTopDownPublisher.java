package authorization_topdown;

import javax.xml.ws.Endpoint;

public class AuthorizationTopDownPublisher
{
    public static void main(String[] args)
    {
        final String URL = "http://localhost:8080/AuthorizationService/authorization";
        AuthorizationServiceImpl service = new AuthorizationServiceImpl();
        Endpoint.publish(URL, service);
        System.out.println("The authorization top down service is published at: " + URL);
    }
}
