#!/bin/sh

#    -v /home/user/docker-compose-ui/demo-projects:/opt/docker-compose-projects:ro \
#    -v /Users/abcd/.docker/machine/machines/default:/opt/cert:ro \
docker run \
    --name docker-compose-ui \
    -p 5000:5000 \
    -e DOCKER_HOST=https:192.168.99.111:2376 \
    -e DOCKER_TLS_VERIFY=1 \
    -e DOCKER_CERT_PATH="C:\Users\cchabot\.docker\machine\machines\swarm-master" \
    francescou/docker-compose-ui
