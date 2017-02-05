package ticket.testclient;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class TicketTestClient
{
    final QName qName = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "Ticket");

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Specify the URL of the Itinerary Web Service");
            System.exit(-1);
        }

        URL url = getWSDLURL(args[0]);
        TicketTestClient client = new TicketTestClient();
        client.ping(url);
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

    public void ping(URL url)
    {
        Ticket ticket = new Ticket(url, qName);
        System.out.println("Service is " + ticket);
        TicketService port = ticket.getTicketPort();

        System.out.println(port.ping());
    }
}
