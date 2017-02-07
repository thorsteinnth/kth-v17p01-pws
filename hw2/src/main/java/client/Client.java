package client;

import client.generated.authorization.Authorization;
import client.generated.authorization.AuthorizationService;
import client.generated.authorization.InvalidCredentials_Exception;
import client.generated.authorization.User;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the real client that talks to all services.
 * */
public class Client
{
    private final QName authorizationQName = new QName("http://hw2.flightticketreservation/authorization.service/authorization", "Authorization");

    private AuthorizationService authorizationService;

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("Parameter error: Specify the URL of the Authorization, Itinerary and Ticket web services");
            System.exit(-1);
        }

        URL authorizationUrl = getWSDLURL(args[0]);

        new Client(authorizationUrl).run();
    }

    public Client(URL authorizationUrl)
    {
        Authorization authorization = new Authorization(authorizationUrl, authorizationQName);
        System.out.println("Authorization service is " + authorization);
        this.authorizationService = authorization.getAuthorizationPort();
    }

    private void run()
    {
        if (!getAuthorization())
            return;
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
            return true;
        }
        catch (InvalidCredentials_Exception ex)
        {
            System.out.println("Invalid credentials for " + user.getUsername());
            return false;
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
}
