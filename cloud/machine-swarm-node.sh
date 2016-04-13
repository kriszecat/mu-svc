#!/bin/sh

docker-machine create -d virtualbox \
 --virtualbox-disk-size "2000" -virtualbox-cpu-count "1" --virtualbox-memory "512" \
 --engine-insecure-registry $(docker-machine ip registry):5000 \
 --swarm --swarm-discovery consul://$(docker-machine ip discovery):8500 \
 --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500 \
 --engine-opt cluster-advertise=eth1:2376 \
 --engine-label capacity=$2 \
 swarm-$1
