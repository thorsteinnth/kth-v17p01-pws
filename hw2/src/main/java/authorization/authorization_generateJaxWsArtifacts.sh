#!/bin/bash

echo "Compiling authorization beans and service"

javac exception/*.java bean/*.java service/*.java

echo "Generating JAX-WS artifacts"

wsgen -r . -d "../" -cp "../" authorization.service.AuthorizationService -wsdl
