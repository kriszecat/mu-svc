#!/bin/sh

eval $(docker-machine env --swarm swarm-master)
docker-compose -f docker-compose-services.yml up -d
docker-compose -f docker-compose-services.yml scale activity-service=2
docker ps -a
