#!/bin/bash

echo "Compiling itinerary service publisher"

javac -cp "../" publish/*.java

echo "Running itinerary service publisher"

java -cp "../" itinerary.publish.ItineraryServicePublisher
