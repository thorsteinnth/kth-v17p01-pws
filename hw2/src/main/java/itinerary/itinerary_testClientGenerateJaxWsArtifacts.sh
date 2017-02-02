 #!/bin/bash

echo "Generating itinerary test client JAX-WS artifacts"

wsimport -keep -d "../" -cp "../" -p itinerary.testclient http://localhost:8080/ItineraryService/itinerary?wsdl