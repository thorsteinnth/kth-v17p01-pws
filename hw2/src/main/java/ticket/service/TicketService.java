package ticket.service;

import shared.Flight;
import shared.Itinerary;
import shared.SharedData;
import ticket.bean.BookableItinerary;
import ticket.bean.PaymentInfo;
import ticket.bean.TicketContainer;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@WebService(serviceName = "Ticket",
        portName = "TicketPort",
        targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class TicketService {

    ArrayList<TicketContainer> ticketContainers;

    public TicketService() {
        generateTickets();
    }

    @WebMethod
    public ArrayList<BookableItinerary> getPriceAndAvailabilityOfItineraries(ArrayList<Itinerary> itineraries) {
        ArrayList<BookableItinerary> bookableItineraries = new ArrayList<>();

        return bookableItineraries;
    }

    @WebMethod
    public String bookItinerary(BookableItinerary itinerary, PaymentInfo paymentInfo) {

        return "Itinerary has been booked";
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
