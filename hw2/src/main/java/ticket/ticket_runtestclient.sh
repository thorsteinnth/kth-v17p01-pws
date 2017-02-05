#!/bin/bash

echo "Compiling ticket test client"

javac -cp "../" ../shared/AuthSOAPHandlerClient.java testclient/TicketTestClient.java

echo "Running ticket test client"

java -cp "../" ticket.testclient.TicketTestClient http://localhost:8080/TicketService/ticket?wsdl