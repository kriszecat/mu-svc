#!/bin/sh

eval $(docker-machine env registry)
docker-compose -f docker-compose-registry.yml up -d
docker ps -a
