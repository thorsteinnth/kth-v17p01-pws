#!/bin/bash

echo "Compiling itinerary service publisher"

javac -cp "../" publish/*.java

echo "Running itinerary service publisher"

java -cp "../:../../../../lib/jgrapht-core-1.0.1.jar" itinerary.publish.ItineraryServicePublisher
