package resources;

import bean.*;
import exceptions.InvalidItineraryException;
import exceptions.PaymentValidationException;
import storage.TicketStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;

@Path("/booking")
public class BookingResource
{
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    public BookingResource()
    {}

    public BookingResource(UriInfo uriInfo, Request request)
    {
        this.uriInfo = uriInfo;
        this.request = request;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response bookItinerary(JAXBElement<BookingRequest> jaxbBookingRequest)
    {
        BookingRequest bookingRequest = jaxbBookingRequest.getValue();

        try
        {
            validateRequestedItinerary(bookingRequest.getItinerary());
            validatePaymentInfo(bookingRequest.getPaymentInfo());
        }
        catch (InvalidItineraryException|PaymentValidationException ex)
        {
            // NOTE: Different HTTP codes should be used here
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        // Book and Issue tickets for each flight in the itinerary
        // We subtract the number of tickets being booked from the total number of available tickets
        // for each of the flights in the itinerary

        ArrayList<Ticket> tickets = new ArrayList<>();

        for (Flight flight : bookingRequest.getItinerary().getFlights())
        {
            Ticket ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setPaymentInfo(bookingRequest.getPaymentInfo());
            ticket.setBooked(true);
            ticket.setDate(bookingRequest.getItinerary().getDate());

            TicketContainer tc = TicketStore.getTicketStore().getTicketContainer(flight.getFlightNumber());
            tc.setNumberOfAvailableTickets(tc.getNumberOfAvailableTickets() - 1);

            ticket.setIssued(true);
            tickets.add(ticket);
        }

        GenericEntity<List<Ticket>> genericEntityTicketList = new GenericEntity<List<Ticket>>(tickets){};
        return Response.ok(genericEntityTicketList).build();
    }

    private void validateRequestedItinerary(Itinerary itinerary) throws InvalidItineraryException
    {
        // We would check here if the itinerary is still valid
        // For example, it may have sold out in the meantime
        if (itinerary == null)
            throw new InvalidItineraryException("The requested itinerary is invalid");
    }

    private void validatePaymentInfo(PaymentInfo paymentInfo) throws PaymentValidationException
    {
        // We would check here if the given card goes through
        if (paymentInfo == null)
            throw new PaymentValidationException("Payment information invalid");
    }
}
