#!/bin/sh

docker-machine create -d virtualbox \
--virtualbox-disk-size "10000" --virtualbox-cpu-count "1" --virtualbox-memory "512" \
registry
