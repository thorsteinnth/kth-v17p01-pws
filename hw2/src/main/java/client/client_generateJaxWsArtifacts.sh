#!/bin/bash

echo "Generating client JAX-WS artifacts ..."

echo "Generating client authorization JAX-WS artifacts ..."
wsimport -keep -b "client_authorization_jaxws_bindings.xml" -d "../" -cp "../" -p client.generated.authorization http://localhost:12501/AuthorizationService/authorization?wsdl

echo "Generating client itinerary JAX-WS artifacts ..."
wsimport -keep -b "client_itinerary_jaxws_bindings.xml" -d "../" -cp "../" -p client.generated.itinerary http://localhost:12502/ItineraryService/itinerary?wsdl

echo "Generating client ticket JAX-WS artifacts ..."
wsimport -keep -b "client_ticket_jaxws_bindings.xml" -d "../" -cp "../" -p client.generated.ticket http://localhost:12503/TicketService/ticket?wsdl