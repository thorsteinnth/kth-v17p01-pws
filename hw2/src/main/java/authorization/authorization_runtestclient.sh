#!/bin/bash

echo "Compiling test client"

javac -cp "../" testclient/AuthorizationTestClient.java

echo "Running test client"

java -cp "../" authorization.testclient.AuthorizationTestClient http://localhost:8080/AuthorizationService/authorization?wsdl