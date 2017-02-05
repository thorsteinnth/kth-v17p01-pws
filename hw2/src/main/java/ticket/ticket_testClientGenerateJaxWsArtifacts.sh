 #!/bin/bash

echo "Generating ticket test client JAX-WS artifacts"

wsimport -keep -b "testclient/ticket_jaxws_bindings.xml" -d "../" -cp "../" -p ticket.testclient http://localhost:8080/TicketService/ticket?wsdl