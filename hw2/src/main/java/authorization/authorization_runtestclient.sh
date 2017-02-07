#!/bin/bash

echo "Compiling authorization test client"

javac -cp "../" testclient/AuthorizationTestClient.java

echo "Running authorization test client"

java -cp "../" authorization.testclient.AuthorizationTestClient http://localhost:12501/AuthorizationService/authorization?wsdl