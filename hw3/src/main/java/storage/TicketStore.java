package storage;

import bean.Flight;
import bean.TicketContainer;

import java.util.HashMap;

public class TicketStore {

    // Maps flight number to flight ticket container
    private static HashMap<String, TicketContainer> ticketMap;
    private static TicketStore instance = null;

    public TicketStore() {

        ticketMap = generateTicketMap();
    }

    public static TicketStore getTicketStore()
    {
        if (instance == null)
            instance = new TicketStore();

        return instance;
    }

    public TicketContainer getTicketContainer(String flightNumber) {

        return ticketMap.get(flightNumber);
    }

    private HashMap<String, TicketContainer> generateTicketMap()
    {
        HashMap<String, TicketContainer> map = new HashMap<>();

        for (Flight flight : FlightStore.getFlightStore().getFlights())
        {
            TicketContainer ticketContainer = new TicketContainer();
            ticketContainer.setNumberOfAvailableTickets(20);
            ticketContainer.setPrice(2500);
            ticketContainer.setDate("2017-02-13");

            map.put(flight.getFlightNumber(), ticketContainer);
        }

        return map;
    }
}
