#!/bin/sh

eval $(docker-machine env --swarm swarm-master)
export ENV_DOCKER_REGISTRY_HOST=$(docker-machine ip registry)
docker-compose -f docker-compose-services.yml down
docker-compose -f docker-compose-services.yml up -d
#docker-compose -f docker-compose-services.yml scale activity-service=2
docker ps -a
