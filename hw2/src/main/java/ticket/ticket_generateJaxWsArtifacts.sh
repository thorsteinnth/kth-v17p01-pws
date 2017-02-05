#!/bin/bash

echo "Compiling ticket beans and service"

javac ../shared/*.java bean/*.java service/*.java

echo "Generating JAX-WS artifacts"

wsgen -r . -d "../" -cp "../" ticket.service.TicketService -wsdl
