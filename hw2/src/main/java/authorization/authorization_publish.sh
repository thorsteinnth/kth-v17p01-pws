#!/bin/bash

echo "Compiling publisher"

javac -cp "../" publish/*.java

echo "Running publisher"

java -cp "../" authorization.publish.AuthorizationServicePublisher
