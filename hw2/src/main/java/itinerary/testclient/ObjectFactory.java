
package itinerary.testclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the itinerary.testclient package. 
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

    private final static QName _NoRouteFoundException_QNAME = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "NoRouteFoundException");
    private final static QName _FindItinerary_QNAME = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "findItinerary");
    private final static QName _FindItineraryResponse_QNAME = new QName("http://hw2.flightticketreservation/itinerary.service/itinerary", "findItineraryResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: itinerary.testclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NoRouteFoundException }
     * 
     */
    public NoRouteFoundException createNoRouteFoundException() {
        return new NoRouteFoundException();
    }

    /**
     * Create an instance of {@link FindItinerary }
     * 
     */
    public FindItinerary createFindItinerary() {
        return new FindItinerary();
    }

    /**
     * Create an instance of {@link FindItineraryResponse }
     * 
     */
    public FindItineraryResponse createFindItineraryResponse() {
        return new FindItineraryResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link NoRouteFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", name = "NoRouteFoundException")
    public JAXBElement<NoRouteFoundException> createNoRouteFoundException(NoRouteFoundException value) {
        return new JAXBElement<NoRouteFoundException>(_NoRouteFoundException_QNAME, NoRouteFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindItinerary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", name = "findItinerary")
    public JAXBElement<FindItinerary> createFindItinerary(FindItinerary value) {
        return new JAXBElement<FindItinerary>(_FindItinerary_QNAME, FindItinerary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindItineraryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", name = "findItineraryResponse")
    public JAXBElement<FindItineraryResponse> createFindItineraryResponse(FindItineraryResponse value) {
        return new JAXBElement<FindItineraryResponse>(_FindItineraryResponse_QNAME, FindItineraryResponse.class, null, value);
    }

}
