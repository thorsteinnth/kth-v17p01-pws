package ticket.publish;

import ticket.service.TicketService;
import javax.xml.ws.Endpoint;

public class TicketServicePublisher {

    public static void main(String[] args) {
        final String WSUrl = "http://localhost:12503/TicketService/ticket";
        Endpoint.publish(WSUrl, new TicketService());
        System.out.println("The ticket service is published at: " + WSUrl);
    }
}
