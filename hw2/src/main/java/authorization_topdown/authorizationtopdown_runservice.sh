#!/usr/bin/env bash

echo "Generating authorization top down artifacts"

sh authorizationtopdown_generateJaxWsArtifacts.sh

echo "Compiling authorization top down service and publisher"

javac ../shared/*.java generated/*.java *.java

echo "Publishing service"

java -cp "../" authorization_topdown.AuthorizationTopDownPublisher