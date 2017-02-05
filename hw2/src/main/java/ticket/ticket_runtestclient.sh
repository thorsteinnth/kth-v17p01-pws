#!/bin/bash

echo "Compiling ticket test client"

javac -cp "../" testclient/TicketTestClient.java

echo "Running ticket test client"

java -cp "../" ticket.testclient.TicketTestClient http://localhost:8080/TicketService/ticket?wsdl