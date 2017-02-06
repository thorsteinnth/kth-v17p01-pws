package ticket.testclient;

import com.sun.xml.internal.ws.client.ClientTransportException;

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
        Ticket_Service ticketService = new Ticket_Service(url, qName);
        System.out.println("Service is " + ticketService);
        TicketService port = ticketService.getTicketPort();

        try
        {
            System.out.println(port.ping());
        }
        catch (ClientTransportException ex)
        {
            System.out.println(ex);
        }
    }
}
