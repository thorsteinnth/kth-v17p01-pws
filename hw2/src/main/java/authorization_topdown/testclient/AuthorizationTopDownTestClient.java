package authorization_topdown.testclient;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthorizationTopDownTestClient
{
    final QName qName = new QName("http://hw2.flightticketreservation/authorization.service/authorization/", "AuthorizationServiceImplService");

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Specify the URL of the Authorization Web Service");
            System.exit(-1);
        }

        URL url = getWSDLURL(args[0]);
        AuthorizationTopDownTestClient client = new AuthorizationTopDownTestClient();
        client.getAuthorization(url);
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

    public void getAuthorization(URL url)
    {
        AuthorizationServiceImplService authorizationService = new AuthorizationServiceImplService(url, qName);
        System.out.println("Service is " + authorizationService);
        AuthorizationService port = authorizationService.getAuthorizationServicePort();

        User user1 = new User();
        user1.setUsername("John");
        user1.setPassword("wrongpass");

        System.out.println("Requesting authorization for: " + printUser(user1));

        try
        {
            User userResponse = port.authorizeUser(user1);
            System.out.println("User response: " + printUser(userResponse));
        }
        catch (InvalidCredentialsException ex)
        {
            System.out.println("Invalid credentials for " + user1.getUsername());
        }

        User user2 = new User();
        user2.setUsername("user1");
        user2.setPassword("pass1");

        System.out.println("Requesting authorization for: " + printUser(user2));

        try
        {
            User userResponse = port.authorizeUser(user2);
            System.out.println("User response: " + printUser(userResponse));
        }
        catch (InvalidCredentialsException ex)
        {
            System.out.println("Invalid credentials for " + user2.getUsername());
        }
    }

    private String printUser(User user)
    {
        return "User{" +
                "password='" + user.getPassword() + '\'' +
                ", token='" + user.getToken() + '\'' +
                ", username='" + user.getUsername() + '\'' +
                '}';
    }
}
