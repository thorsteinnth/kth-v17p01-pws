package itinerary.publish;

import itinerary.service.ItineraryService;

import javax.xml.ws.Endpoint;

public class ItineraryServicePublisher
{
    public static void main(String[] args)
    {
        final String WSUrl = "http://localhost:12502/ItineraryService/itinerary";
        Endpoint.publish(WSUrl, new ItineraryService());
        System.out.println("The itinerary service is published at: " + WSUrl);
    }
}
