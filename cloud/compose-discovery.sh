#!/bin/sh

eval $(docker-machine env discovery)
docker-compose -f docker-compose-consul.yml up -d
docker ps -a
