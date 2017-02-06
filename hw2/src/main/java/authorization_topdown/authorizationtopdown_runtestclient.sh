#!/bin/bash

echo "Compiling authorization top down test client"

javac -cp "../" testclient/AuthorizationTopDownTestClient.java

echo "Running authorization top down test client"

java -cp "../" authorization_topdown.testclient.AuthorizationTopDownTestClient http://localhost:8080/AuthorizationService/authorization?wsdl