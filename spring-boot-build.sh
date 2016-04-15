#!/bin/sh

eval $(docker-machine env swarm-master)
mvn clean install -Pcloud