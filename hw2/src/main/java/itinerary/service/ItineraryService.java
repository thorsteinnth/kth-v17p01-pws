package itinerary.service;

import itinerary.bean.Flight;
import itinerary.bean.Itinerary;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName = "Itinerary",
        portName = "AuthorizationPort",
        targetNamespace = "http://hw2.flightticketreservation/authorization.service/authorization")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class ItineraryService
{
    @WebMethod
    public Itinerary findItinerary(Flight flight) {

        Itinerary it = new Itinerary();
        return it;
    }
}
