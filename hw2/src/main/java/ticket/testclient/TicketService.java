
package ticket.testclient;

import java.util.List;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "TicketService", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")
@HandlerChain(file = "TicketService_handler.xml")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TicketService {


    /**
     * 
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "ping", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket", className = "ticket.testclient.Ping")
    @ResponseWrapper(localName = "pingResponse", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket", className = "ticket.testclient.PingResponse")
    @Action(input = "http://hw2.flightticketreservation/ticket.service/ticket/TicketService/pingRequest", output = "http://hw2.flightticketreservation/ticket.service/ticket/TicketService/pingResponse")
    public boolean ping();

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ticket.testclient.BookableItinerary>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPriceAndAvailabilityOfItinerariesForDate", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket", className = "ticket.testclient.GetPriceAndAvailabilityOfItinerariesForDate")
    @ResponseWrapper(localName = "getPriceAndAvailabilityOfItinerariesForDateResponse", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket", className = "ticket.testclient.GetPriceAndAvailabilityOfItinerariesForDateResponse")
    @Action(input = "http://hw2.flightticketreservation/ticket.service/ticket/TicketService/getPriceAndAvailabilityOfItinerariesForDateRequest", output = "http://hw2.flightticketreservation/ticket.service/ticket/TicketService/getPriceAndAvailabilityOfItinerariesForDateResponse")
    public List<BookableItinerary> getPriceAndAvailabilityOfItinerariesForDate(
        @WebParam(name = "arg0", targetNamespace = "")
        List<Itinerary> arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<ticket.testclient.Ticket>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "bookItinerary", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket", className = "ticket.testclient.BookItinerary")
    @ResponseWrapper(localName = "bookItineraryResponse", targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket", className = "ticket.testclient.BookItineraryResponse")
    @Action(input = "http://hw2.flightticketreservation/ticket.service/ticket/TicketService/bookItineraryRequest", output = "http://hw2.flightticketreservation/ticket.service/ticket/TicketService/bookItineraryResponse")
    public List<Ticket> bookItinerary(
        @WebParam(name = "arg0", targetNamespace = "")
        BookableItinerary arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        PaymentInfo arg1);

}
