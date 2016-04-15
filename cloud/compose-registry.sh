#!/bin/sh

eval $(docker-machine env registry)
docker-compose -f docker-compose-registry.yml up -d
docker ps -a

#IMAGE=java:8-jdk
#docker pull ${IMAGE} && docker tag ${IMAGE} $(docker-machine ip registry):5000/${IMAGE}
#docker push $(docker-machine ip registry):5000/${IMAGE}
#docker rmi ${IMAGE} && docker rmi $(docker-machine ip registry):5000/${IMAGE}
