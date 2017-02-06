#!/bin/bash

echo "Generating authorization top down test client JAX-WS artifacts"

wsimport -keep -d "../" -cp "../" -p authorization_topdown.testclient http://localhost:8080/AuthorizationService/authorization?wsdl