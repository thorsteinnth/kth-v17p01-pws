#!/bin/bash

echo "Compiling itinerary beans and service"

javac -cp "../../../../lib/jgrapht-core-1.0.1.jar" ../shared/*.java exception/*.java bean/*.java service/*.java

echo "Generating JAX-WS artifacts"

wsgen -r . -d "../" -cp "../:../../../../lib/jgrapht-core-1.0.1.jar" itinerary.service.ItineraryService -wsdl
