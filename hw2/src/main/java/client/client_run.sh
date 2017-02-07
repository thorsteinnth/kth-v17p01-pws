#!/bin/bash

echo "Compiling client"

javac -cp "../" Client.java

echo "Running client"

java -cp "../" client.Client http://localhost:12501/AuthorizationService/authorization?wsdl http://localhost:12502/ItineraryService/itinerary?wsdl http://localhost:12503/TicketService/ticket?wsdl