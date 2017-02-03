package ticket.service;

import shared.Flight;
import shared.SharedData;
import ticket.bean.TicketContainer;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@WebService(serviceName = "TicketContainer",
        portName = "TicketPort",
        targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class TicketService {

    ArrayList<TicketContainer> ticketContainers;

    public TicketService() {
        generateTickets();
    }

    //TODO : Web method that takes in itineraries and returns prices of available itineraries

    private void generateTickets() {
        this.ticketContainers = new ArrayList<>();

        for (Flight flight : SharedData.getFlights()) {
            TicketContainer ticketContainer = new TicketContainer();
            ticketContainer.setFlight(flight);
            ticketContainer.setNumberOfAvailableTickets(100);
            ticketContainer.setPrice(new BigDecimal(200));
            ticketContainer.setDate(new Date());
        }

    }


}
