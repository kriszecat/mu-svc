#!/bin/sh

docker-machine create -d virtualbox \
  --virtualbox-disk-size "5000" --virtualbox-cpu-count "1" --virtualbox-memory "1024" \
  --engine-insecure-registry $(docker-machine ip registry):5000 \
  rancher
eval $(docker-machine env rancher)
docker run -d --restart=always -p 8080:8080 rancher/server
docker images
docker ps
