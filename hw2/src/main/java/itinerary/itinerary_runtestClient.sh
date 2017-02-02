#!/bin/bash

echo "Compiling test client"

javac -cp "../" testclient/ItineraryTestClient.java

echo "Running test client"

java -cp "../" itinerary.testclient.ItineraryTestClient http://localhost:8080/ItineraryService/itinerary?wsdl