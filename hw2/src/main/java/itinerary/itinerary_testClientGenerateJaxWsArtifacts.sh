 #!/bin/bash

echo "Generating itinerary test client JAX-WS artifacts"

wsimport -keep -b "testclient/itinerary_jaxws_bindings.xml" -d "../" -cp "../" -p itinerary.testclient http://localhost:12502/ItineraryService/itinerary?wsdl