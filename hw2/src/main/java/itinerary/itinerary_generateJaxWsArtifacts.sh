#!/bin/bash

echo "Compiling itinerary beans and service"

javac bean/*.java service/*.java

echo "Generating JAX-WS artifacts"

wsgen -r . -d "../" -cp "../" itinerary.service.ItineraryService -wsdl
