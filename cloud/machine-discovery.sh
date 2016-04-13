#!/bin/sh

docker-machine create -d virtualbox \
--virtualbox-disk-size "2000" --virtualbox-cpu-count "1" --virtualbox-memory "512" \
--engine-insecure-registry http://$(docker-machine ip registry):5000 \
discovery
