#!/bin/bash

echo "Compiling authorization service publisher"

javac -cp "../" publish/*.java

echo "Running authorization service publisher"

java -cp "../" authorization.publish.AuthorizationServicePublisher
