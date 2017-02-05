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
import java.util.HashMap;

@WebService(serviceName = "Ticket",
        portName = "TicketPort",
        targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class TicketService {

    ArrayList<TicketContainer> ticketContainers;
    HashMap<Flight, TicketContainer> ticketMap;

    public TicketService()
    {
        generateTickets();
        generateTicketMap();
    }

    @WebMethod
    public boolean ping(Flight flight)
    {
        return true;
    }

    private void generateTicketMap()
    {
        this.ticketMap = new HashMap<>();

        for (Flight flight : SharedData.getFlights())
        {
            TicketContainer ticketContainer = new TicketContainer();
            ticketContainer.setFlight(flight);
            ticketContainer.setNumberOfAvailableTickets(100);
            ticketContainer.setPrice(new BigDecimal(200));
            ticketContainer.setDate(new Date());

            this.ticketMap.put(flight, ticketContainer);
        }

        System.out.println(this.ticketMap);
    }

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
