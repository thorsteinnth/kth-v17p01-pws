package ticket.service;

import shared.Flight;
import shared.Itinerary;
import shared.SharedData;
import ticket.bean.BookableItinerary;
import ticket.bean.PaymentInfo;
import ticket.bean.Ticket;
import ticket.bean.TicketContainer;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@WebService(serviceName = "Ticket",
        portName = "TicketPort",
        targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
@HandlerChain(file= "ticket_handler.xml")
public class TicketService
{
    // Maps flight number to flight ticket container
    HashMap<String, TicketContainer> ticketMap;

    public TicketService()
    {
        generateTicketMap();
    }

    @WebMethod
    public boolean ping()
    {
        return true;
    }

    @WebMethod
    public ArrayList<BookableItinerary> getPriceAndAvailabilityOfItinerariesForDate(
            ArrayList<Itinerary> itineraries, String date) {

        if(itineraries.size() < 1) {
            return null;
        }

        ArrayList<BookableItinerary> bookableItineraries = new ArrayList<>();

        for (Itinerary itinerary : itineraries) {
            BookableItinerary bookableItinerary;

            int totalPriceForItinerary = 0;
            int numberOfAvailableTickets = Integer.MAX_VALUE;

            for(Flight flight : itinerary.getFlights()) {
                TicketContainer tc = this.ticketMap.get(flight.getFlightNumber());

                // check if the requested date is the same as for the flight ticket container
                if(tc != null && date.equals(tc.getDate())) {
                    totalPriceForItinerary += tc.getPrice();
                    numberOfAvailableTickets = Integer.min(numberOfAvailableTickets, tc.getNumberOfAvailableTickets());
                }
                else {
                    numberOfAvailableTickets = 0;
                    break;
                }
            }

            if(numberOfAvailableTickets > 0) {
                bookableItinerary = new BookableItinerary(
                        itinerary.getFlights(), date, totalPriceForItinerary, numberOfAvailableTickets);
                bookableItineraries.add(bookableItinerary);
            }
        }

        return bookableItineraries;
    }

    @WebMethod
    public ArrayList<Ticket> bookItinerary(BookableItinerary itinerary, PaymentInfo paymentInfo) {

        // Payment processing could be done here
        // Book and Issue tickets for each flight in the itinerary
        // We subtract the number of tickets being booked from the total number of available tickets
        // for each of the flights in the itinerary

        ArrayList<Ticket> tickets = new ArrayList<>();

        for (Flight flight : itinerary.getFlights()) {

            Ticket ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setPaymentInfo(paymentInfo);
            ticket.setBooked(true);
            ticket.setDate(itinerary.getDate());

            TicketContainer tc = this.ticketMap.get(flight.getFlightNumber());
            tc.setNumberOfAvailableTickets(tc.getNumberOfAvailableTickets() - 1);

            ticket.setIssued(true);
            tickets.add(ticket);
        }

        return tickets;
    }

    private void generateTicketMap()
    {
        this.ticketMap = new HashMap<>();

        for (Flight flight : SharedData.getFlights())
        {
            TicketContainer ticketContainer = new TicketContainer();
            ticketContainer.setNumberOfAvailableTickets(20);
            ticketContainer.setPrice(2500);
            ticketContainer.setDate("2017-02-07");

            this.ticketMap.put(flight.getFlightNumber(), ticketContainer);
        }
    }
}
