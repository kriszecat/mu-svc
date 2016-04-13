#!/bin/sh

mvn clean package
docker build -t hq/admin .
docker tag hq/admin $(docker-machine ip registry):5000/hq/admin
docker push $(docker-machine ip registry):5000/hq/admin
docker rmi -f $(docker images -q hq/admin)
docker rmi -f $(docker images -q java)
