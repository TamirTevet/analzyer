#!/bin/sh

docker build -t anal .
docker tag anal:latest team2registry.azurecr.io/analyzer:v1
docker push team2registry.azurecr.io/analyzer:v1