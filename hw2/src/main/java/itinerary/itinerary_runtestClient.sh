#!/bin/bash

echo "Compiling itinerary test client"

javac -cp "../" testclient/ItineraryTestClient.java

echo "Running itinerary test client"

java -cp "../" itinerary.testclient.ItineraryTestClient http://localhost:8080/ItineraryService/itinerary?wsdl