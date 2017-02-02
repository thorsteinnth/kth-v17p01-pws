package itinerary.service;

import itinerary.bean.Flight;
import itinerary.bean.Itinerary;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService(serviceName = "Itinerary",
        portName = "ItineraryPort",
        targetNamespace = "http://hw2.flightticketreservation/itinerary.service/itinerary")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class ItineraryService
{
    @WebMethod
    public Itinerary findItinerary(Flight flight) {

        Itinerary it = new Itinerary();

        ArrayList<Flight> flights = new ArrayList<Flight>();

        Flight flight1 = new Flight();
        flight1.setDeparture("Reykjavik");
        flight1.setDestination("Stockholm");
        flights.add(flight1);

        Flight flight2 = new Flight();
        flight2.setDeparture("Stockholm");
        flight2.setDestination("Tallinn");
        flights.add(flight2);

        it.setFlights(flights);

        return it;
    }
}
