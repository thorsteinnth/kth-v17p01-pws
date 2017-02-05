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
import java.util.HashMap;

@WebService(serviceName = "Ticket",
        portName = "TicketPort",
        targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class TicketService
{
    HashMap<Flight, TicketContainer> ticketMap;

    public TicketService()
    {
        generateTicketMap();
    }

    @WebMethod
    public boolean ping(Flight flight)
    {
        return true;
    }

    @WebMethod
    public ArrayList<BookableItinerary> getPriceAndAvailabilityOfItineraries(ArrayList<Itinerary> itineraries) {

        if(itineraries.size() < 1) {
            return null;
        }

        ArrayList<BookableItinerary> bookableItineraries = new ArrayList<>();

        for (Itinerary itinerary : itineraries) {
            BookableItinerary bookableItinerary;

            int totalPriceForItinerary = 0;
            int numberOfAvailableTickets = Integer.MAX_VALUE;

            for(Flight flight : itinerary.getFlights()) {
                TicketContainer tc = this.ticketMap.get(flight);
                totalPriceForItinerary += tc.getPrice();
                numberOfAvailableTickets = Integer.min(numberOfAvailableTickets, tc.getNumberOfAvailableTickets());
            }

            bookableItinerary = new BookableItinerary(
                    itinerary.getFlights(), totalPriceForItinerary, numberOfAvailableTickets);
            bookableItineraries.add(bookableItinerary);
        }

        return bookableItineraries;
    }

    @WebMethod
    public String bookItinerary(BookableItinerary itinerary, PaymentInfo paymentInfo) {

        return "Itinerary has been booked";
    }

    private void generateTicketMap()
    {
        this.ticketMap = new HashMap<>();

        for (Flight flight : SharedData.getFlights())
        {
            TicketContainer ticketContainer = new TicketContainer();
            ticketContainer.setNumberOfAvailableTickets(100);
            ticketContainer.setPrice(2000);
            ticketContainer.setDate(new Date());

            this.ticketMap.put(flight, ticketContainer);
        }

        System.out.println(this.ticketMap);
    }
}
