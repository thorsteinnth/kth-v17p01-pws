#!/bin/bash

echo "Generating authorization test client JAX-WS artifacts"

wsimport -keep -d "../" -cp "../" -p authorization.testclient http://localhost:12501/AuthorizationService/authorization?wsdl