
package itinerary.testclient;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ItineraryService", targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ItineraryService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<itinerary.testclient.Itinerary>
     * @throws NoRouteFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findItineraries", targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", className = "itinerary.testclient.FindItineraries")
    @ResponseWrapper(localName = "findItinerariesResponse", targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary", className = "itinerary.testclient.FindItinerariesResponse")
    @Action(input = "http://hw2.flightticketreservation/itinerary.service/itinerary/ItineraryService/findItinerariesRequest", output = "http://hw2.flightticketreservation/itinerary.service/itinerary/ItineraryService/findItinerariesResponse", fault = {
        @FaultAction(className = NoRouteFoundException_Exception.class, value = "http://hw2.flightticketreservation/itinerary.service/itinerary/ItineraryService/findItineraries/Fault/NoRouteFoundException")
    })
    public List<Itinerary> findItineraries(
        @WebParam(name = "arg0", targetNamespace = "")
        Flight arg0)
        throws NoRouteFoundException_Exception
    ;

}