
package client.generated.itinerary;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.generated.itinerary package. 
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

    private final static QName _FindItinerariesResponse_QNAME = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "findItinerariesResponse");
    private final static QName _FindItineraries_QNAME = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "findItineraries");
    private final static QName _NoRouteFoundException_QNAME = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "NoRouteFoundException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.generated.itinerary
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindItineraries }
     * 
     */
    public FindItineraries createFindItineraries() {
        return new FindItineraries();
    }

    /**
     * Create an instance of {@link FindItinerariesResponse }
     * 
     */
    public FindItinerariesResponse createFindItinerariesResponse() {
        return new FindItinerariesResponse();
    }

    /**
     * Create an instance of {@link NoRouteFoundException }
     * 
     */
    public NoRouteFoundException createNoRouteFoundException() {
        return new NoRouteFoundException();
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
     * Create an instance of {@link Itinerary }
     * 
     */
    public Itinerary createItinerary() {
        return new Itinerary();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindItinerariesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", name = "findItinerariesResponse")
    public JAXBElement<FindItinerariesResponse> createFindItinerariesResponse(FindItinerariesResponse value) {
        return new JAXBElement<FindItinerariesResponse>(_FindItinerariesResponse_QNAME, FindItinerariesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindItineraries }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", name = "findItineraries")
    public JAXBElement<FindItineraries> createFindItineraries(FindItineraries value) {
        return new JAXBElement<FindItineraries>(_FindItineraries_QNAME, FindItineraries.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoRouteFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", name = "NoRouteFoundException")
    public JAXBElement<NoRouteFoundException> createNoRouteFoundException(NoRouteFoundException value) {
        return new JAXBElement<NoRouteFoundException>(_NoRouteFoundException_QNAME, NoRouteFoundException.class, null, value);
    }

}
