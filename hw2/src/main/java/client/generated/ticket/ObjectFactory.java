
package client.generated.ticket;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.generated.ticket package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BookItineraryResponse_QNAME = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "bookItineraryResponse");
    private final static QName _BookItinerary_QNAME = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "bookItinerary");
    private final static QName _PingResponse_QNAME = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "pingResponse");
    private final static QName _GetPriceAndAvailabilityOfItinerariesForDateResponse_QNAME = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "getPriceAndAvailabilityOfItinerariesForDateResponse");
    private final static QName _GetPriceAndAvailabilityOfItinerariesForDate_QNAME = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "getPriceAndAvailabilityOfItinerariesForDate");
    private final static QName _Ping_QNAME = new QName("http://hw2.flightticketreservation/ticket.service/ticket", "ping");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.generated.ticket
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPriceAndAvailabilityOfItinerariesForDate }
     * 
     */
    public GetPriceAndAvailabilityOfItinerariesForDate createGetPriceAndAvailabilityOfItinerariesForDate() {
        return new GetPriceAndAvailabilityOfItinerariesForDate();
    }

    /**
     * Create an instance of {@link GetPriceAndAvailabilityOfItinerariesForDateResponse }
     * 
     */
    public GetPriceAndAvailabilityOfItinerariesForDateResponse createGetPriceAndAvailabilityOfItinerariesForDateResponse() {
        return new GetPriceAndAvailabilityOfItinerariesForDateResponse();
    }

    /**
     * Create an instance of {@link Ping }
     * 
     */
    public Ping createPing() {
        return new Ping();
    }

    /**
     * Create an instance of {@link BookItineraryResponse }
     * 
     */
    public BookItineraryResponse createBookItineraryResponse() {
        return new BookItineraryResponse();
    }

    /**
     * Create an instance of {@link BookItinerary }
     * 
     */
    public BookItinerary createBookItinerary() {
        return new BookItinerary();
    }

    /**
     * Create an instance of {@link PingResponse }
     * 
     */
    public PingResponse createPingResponse() {
        return new PingResponse();
    }

    /**
     * Create an instance of {@link Flight }
     * 
     */
    public Flight createFlight() {
        return new Flight();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link BookableItinerary }
     * 
     */
    public BookableItinerary createBookableItinerary() {
        return new BookableItinerary();
    }

    /**
     * Create an instance of {@link Ticket }
     * 
     */
    public Ticket createTicket() {
        return new Ticket();
    }

    /**
     * Create an instance of {@link Itinerary }
     * 
     */
    public Itinerary createItinerary() {
        return new Itinerary();
    }

    /**
     * Create an instance of {@link PaymentInfo }
     * 
     */
    public PaymentInfo createPaymentInfo() {
        return new PaymentInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookItineraryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/ticket.service/ticket", name = "bookItineraryResponse")
    public JAXBElement<BookItineraryResponse> createBookItineraryResponse(BookItineraryResponse value) {
        return new JAXBElement<BookItineraryResponse>(_BookItineraryResponse_QNAME, BookItineraryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookItinerary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/ticket.service/ticket", name = "bookItinerary")
    public JAXBElement<BookItinerary> createBookItinerary(BookItinerary value) {
        return new JAXBElement<BookItinerary>(_BookItinerary_QNAME, BookItinerary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/ticket.service/ticket", name = "pingResponse")
    public JAXBElement<PingResponse> createPingResponse(PingResponse value) {
        return new JAXBElement<PingResponse>(_PingResponse_QNAME, PingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceAndAvailabilityOfItinerariesForDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/ticket.service/ticket", name = "getPriceAndAvailabilityOfItinerariesForDateResponse")
    public JAXBElement<GetPriceAndAvailabilityOfItinerariesForDateResponse> createGetPriceAndAvailabilityOfItinerariesForDateResponse(GetPriceAndAvailabilityOfItinerariesForDateResponse value) {
        return new JAXBElement<GetPriceAndAvailabilityOfItinerariesForDateResponse>(_GetPriceAndAvailabilityOfItinerariesForDateResponse_QNAME, GetPriceAndAvailabilityOfItinerariesForDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceAndAvailabilityOfItinerariesForDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/ticket.service/ticket", name = "getPriceAndAvailabilityOfItinerariesForDate")
    public JAXBElement<GetPriceAndAvailabilityOfItinerariesForDate> createGetPriceAndAvailabilityOfItinerariesForDate(GetPriceAndAvailabilityOfItinerariesForDate value) {
        return new JAXBElement<GetPriceAndAvailabilityOfItinerariesForDate>(_GetPriceAndAvailabilityOfItinerariesForDate_QNAME, GetPriceAndAvailabilityOfItinerariesForDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ping }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/ticket.service/ticket", name = "ping")
    public JAXBElement<Ping> createPing(Ping value) {
        return new JAXBElement<Ping>(_Ping_QNAME, Ping.class, null, value);
    }

}
