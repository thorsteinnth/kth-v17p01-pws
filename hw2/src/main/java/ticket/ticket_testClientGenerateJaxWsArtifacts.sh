 #!/bin/bash

echo "Generating ticket test client JAX-WS artifacts"

wsimport -keep -b "testclient/ticket_jaxws_bindings.xml" -d "../" -cp "../" -p ticket.testclient http://localhost:12503/TicketService/ticket?wsdl