#!/bin/sh

mvn clean install

eval $(docker-machine env --swarm swarm-master)
docker build -t hq/activity .
docker tag hq/activity $(docker-machine ip registry):5000/hq/activity
docker push $(docker-machine ip registry):5000/hq/activity
docker rmi -f $(docker-machine ip registry):5000/hq/activity
docker rmi -f hq/activity
docker rmi -f java:8-jdk
