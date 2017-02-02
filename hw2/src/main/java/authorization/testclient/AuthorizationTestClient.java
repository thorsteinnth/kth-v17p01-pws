package authorization.testclient;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthorizationTestClient
{
    // TODO Change this
    final QName qName = new QName("http://jawxs.ibm.tutorial/jaxws/orderprocess", "OrderProcess");

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Specify the URL of the Authorization Web Service");
            System.exit(-1);
        }

        URL url = getWSDLURL(args[0]);
        AuthorizationTestClient client = new AuthorizationTestClient();
        client.getAuthorization(url);
    }

    private static URL getWSDLURL(String urlStr)
    {
        URL url = null;

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
        // TODO Fill this out when we have generated the client classes
        /*
        //OrderProcess orderProcessingService = new OrderProcess(url, qName);

        System.out.println("Service is" + orderProcessingService);

        OrderBean order = populateOrder();

        OrderProcessService port = orderProcessingService.getOrderProcessPort();
        OrderBean orderResponse = port.processOrder(order);

        System.out.println("Order id is " + orderResponse.getOrderId());
        */
    }
}
