#!/bin/sh

rm -dr dist
grunt clean
grunt build --production
cp -r ../dist .

eval $(docker-machine env --swarm swarm-master)
docker build -t hq/client-ui .
docker tag hq/client-ui $(docker-machine ip registry):5000/hq/client-ui
docker push $(docker-machine ip registry):5000/hq/client-ui
docker rmi -f $(docker-machine ip registry):5000/hq/client-ui
docker rmi -f hq/client-ui
docker rmi -f httpd:2.4
