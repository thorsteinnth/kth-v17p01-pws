#KTH V17P01 Programming Web Services
##Homework 2 (due 07.02.17 23:59)
### Fannar Magnusson (fannar@kth.se) & Thorsteinn Thorri Sigurdsson (ttsi@kth.se)

Instructions to run:

The services and clients are built and run using bash scripts.

Authorization service and test client:

Go into src/main/java/authorization and run the following scripts:
- authorization_runservice.sh
- authorization_testClientGenerateJaxWsArtifacts.sh
- authorization_runtestclient.sh

Authorization top down service and test client:

Go into src/main/java/authorization_topdown and run the following scripts:
- authorizationtopdown_runservice.sh
- authorizationtopdown_testClientGenerateJaxWsArtifacts.sh
- authorizationtopdown_runtestclient.sh

Itinerary service and test client:

Go into src/main/java/itinerary and run the following scripts:
- itinerary_runservice.sh
- itinerary_testClientGenerateJaxWsArtifacts.sh
- itinerary_runtestclient.sh

Ticket service and test client:

Go into src/main/java/ticket and run the following scripts:
- ticket_runservice.sh
- ticket_testClientGenerateJaxWsArtifacts.sh
- ticket_runtestclient.sh

The three services will be published under the following ports:

- Authorization service will be published under localhost, port 12501

- Itinerary service will be published under localhost, port 12502

- Ticket service will be published under localhost, port 12503

Main client

Make sure all three services are up and running (using the normal authorization service, not the top down one).

Go into src/main/java/client and run the following scripts:
- client_generateJaxWsArtifacts.sh
- client_run.sh

The project is also delivered as an IntelliJ project.
It includes run configurations for each of the services and clients,
and will print the SOAP messages sent and received by the test clients to console.