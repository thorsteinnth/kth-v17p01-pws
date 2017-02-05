 #!/bin/bash

echo "Generating ticket test client JAX-WS artifacts"

wsimport -keep -d "../" -cp "../" -p ticket.testclient http://localhost:8080/TicketService/ticket?wsdl