package shared;

import java.util.ArrayList;

public class SharedData
{
    private static ArrayList<Node> nodes;
    private static ArrayList<Flight> flights;
    private static String authToken;

    static
    {
        nodes = generateNodes();
        flights = generateFlights();
        authToken = "thisisanauthenticationtokenthatshouldbebinary";
    }

    public static ArrayList<Node> getNodes()
    {
        return nodes;
    }

    public static ArrayList<Flight> getFlights()
    {
        return flights;
    }

    public static String getAuthToken()
    {
        return authToken;
    }

    private static ArrayList<Node> generateNodes()
    {
        ArrayList<Node> nodes = new ArrayList<>();

        Node node1 = new Node();
        node1.setName("Reykjavik");
        nodes.add(node1);

        Node node2 = new Node();
        node2.setName("Stockholm");
        nodes.add(node2);

        Node node3 = new Node();
        node3.setName("Tallinn");
        nodes.add(node3);

        Node node4 = new Node();
        node4.setName("Helsinki");
        nodes.add(node4);

        Node node5 = new Node();
        node5.setName("Riga");
        nodes.add(node5);

        Node node6 = new Node();
        node6.setName("Vilinius");
        nodes.add(node6);

        return nodes;
    }

    private static ArrayList<Flight> generateFlights()
    {
        ArrayList<Flight> flights = new ArrayList<>();

        // this generates the following flights:
        // Reykjavik -> Stockholm -> Tallinn -> Helsinki -> Riga -> Vilinius
        for (int i = 0; i < nodes.size()-1; i++)
        {
            Flight flight = new Flight();
            flight.setDeparture(nodes.get(i));
            flight.setDestination(nodes.get(i+1));
            flights.add(flight);
        }

        // Reykjavik -> Helsinki
        Flight rvkToHelsinki = new Flight();
        rvkToHelsinki.setDeparture(nodes.get(0));
        rvkToHelsinki.setDestination(nodes.get(3));
        flights.add(rvkToHelsinki);

        return flights;
    }
}
