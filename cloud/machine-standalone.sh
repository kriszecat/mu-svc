#!/bin/sh

docker-machine create -d virtualbox \
--virtualbox-disk-size "5000" --virtualbox-cpu-count "1" --virtualbox-memory "2048" \
--engine-insecure-registry $(docker-machine ip ros-registry):5000 \
standalone
