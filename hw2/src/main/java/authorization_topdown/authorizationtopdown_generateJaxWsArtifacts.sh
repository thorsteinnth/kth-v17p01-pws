#!/bin/bash

echo "Generating authorization top down JAX-WS artifacts"

wsimport -d "../" -s "../" -p authorization_topdown.generated Authorization_topdown.wsdl
