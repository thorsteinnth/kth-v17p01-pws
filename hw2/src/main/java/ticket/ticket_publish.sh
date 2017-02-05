#!/bin/bash

echo "Compiling ticket service publisher"

javac -cp "../" publish/*.java

echo "Running ticket service publisher"

java -cp "../" ticket.publish.TicketServicePublisher
