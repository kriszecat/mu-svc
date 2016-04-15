---
# Création de l'infrastructure du cluster Docker #
NB: Les machines Docker sont créées ici avec le driver virtualbox, notamment parce que ce cluster Docker a été testé sur un poste Windows.
Dans un environnement Linux on premise (VM ou bare metal), on utilisera plutôt le [driver générique](https://docs.docker.com/machine/drivers/generic/ "Docker generic driver").


Lors de la création des machines Docker, on pourra par ailleurs considérer une alternative récente au format de stockage aufs (cf. [Docker and OverlayFS in practice](https://docs.docker.com/engine/userguide/storagedriver/overlayfs-driver/ "Docker and OverlayFS in practice")). Dans ce cas, on ajoutera l'option `--engine-storage-driver overlay` lors de la création de la machine Docker.

## Création du service de registry ##
Création d'une machine Docker pour héberger le service de registry :

    docker-machine create -d virtualbox \
     --virtualbox-disk-size "10000" --virtualbox-cpu-count "1" --virtualbox-memory "512" \
     registry

Lancement du service de registry et du client web associé sur la machine dédiée :<br>
=> ficher docker-compose.yml

    version: '2'

    services:
      image: registry:2
      container_name: registry
      restart: always
      ports:
        - "5000:5000"
      environment:
        - SEARCH_BACKEND=sqlalchemy

      registry-ui:
        image: konradkleine/docker-registry-frontend:v2
        container_name: registry-ui
        ports:
          - "8090:80"
        environment:
          - ENV_DOCKER_REGISTRY_HOST=192.168.99.101
          - ENV_DOCKER_REGISTRY_PORT=5000
        depends_on:
          - registry

=> lancement de la composition

    export ENV_DOCKER_REGISTRY_HOST=$(docker-machine ip registry)
    docker-compose -H $(docker-machine ip registry) up -d

Le client Web permet d'explorer le contenu du serveur de registry privé à l'URL 
`http://$(docker-machine ip registry)/`  

## Création du service de découverte ##
Création d'une machine Docker pour héberger le service de découverte :

    docker-machine create -d virtualbox \
     --virtualbox-disk-size "2000" --virtualbox-cpu-count "1" --virtualbox-memory "512" \
     --engine-insecure-registry http://$(docker-machine ip registry):5000 \
     discovery
    
Lancement du service de découverte sur la machine dédiée :<br>
=> ficher docker-compose.yml

    consul-service:
       image: progrium/consul
       restart: always
       hostname: consul
       ports:
       - 8500:8500
       command: "-server -bootstrap"

=> lancement de la composition

    docker-compose -H $(docker-machine ip discovery) up -d

On utilise le serveur Consul pour la découverte.<br>
Pour la justification du choix de cette solution, cf. [Consul vs. ZooKeeper, doozerd, etcd](https://www.consul.io/intro/vs/zookeeper.html "Consul vs. ZooKeeper, doozerd, etcd")<br>

## Cluster Docker Swarm ##
### Création des machines associées aux noeuds du cluster ###
Création d'un master Swarm qui utilise le service de découverte :

    docker-machine create -d virtualbox \
     --virtualbox-disk-size "2000" --virtualbox-cpu-count "1" --virtualbox-memory "512" \
     --engine-insecure-registry $(docker-machine ip registry):5000 \
     --swarm --swarm-master --swarm-discovery consul://$(docker-machine ip discovery):8500 \
     --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500 \
     --engine-opt cluster-advertise=eth1:2376 \
     swarm-master
    
Création de deux nouveaux noeuds dans le cluster qui utilisent le service de découverte:

    docker-machine create -d virtualbox \
     --virtualbox-disk-size "2000" -virtualbox-cpu-count "1" --virtualbox-memory "512" \
     --engine-insecure-registry $(docker-machine ip registry):5000 \
     --swarm --swarm-discovery consul://$(docker-machine ip discovery):8500 \
     --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500 \
     --engine-opt cluster-advertise=eth1:2376 \
     --engine-label capacity=rest-service \
     swarm-node01
<br>
   
    docker-machine create -d virtualbox \
     --virtualbox-disk-size "2000" -virtualbox-cpu-count "1" --virtualbox-memory "512" \
     --engine-insecure-registry $(docker-machine ip registry):5000 \
     --swarm --swarm-discovery consul://$(docker-machine ip discovery):8500 \
     --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500 \
     --engine-opt cluster-advertise=eth1:2376 \
     --engine-label capacity=rest-service \
     swarm-node02

### Analyse du cluster créé ###
Positionnement de l'environnement client Docker sur le master (noter l'usage de l'option `--swarm`) :

    eval "$(docker-machine env --swarm swarm-master)"

Analyse du cluster

    docker-machine ls
    docker-machine inspect --format='{{json .Driver}}' swarm-master  
    docker info
    
Interrogation du service de découverte pour lister les noeuds appartenant au cluster :


    docker run swarm list consul://$(docker-machine ip discovery):8500

Analyse du réseau virtuel créé par Docker Swarm :

    docker network ls

Pour rejoindre le cluster au besoin après la création de la machine :

    docker run -d swarm join --addr $(docker-machine ip swarm-node01):2376 consul://$(docker-machine ip discovery):8500

<br>
---

# Provisionnement des conteneurs et démarrage du cluster Docker #

## Création de la machine Docker client-ui ##
L'application Angular consomme depuis le navigateur client les services REST fournis par les
serveurs REST à travers l'API Gateway.
Il faut donc que le navigateur connaisse l'URL publique de l'API Gateway.
<br>
Dans la version actuelle du PoC, on n'utilise pas d'infrastructure PaaS permettant de disposer d'un
serveur DNS et d'un load balancer. On inscrit donc pour le moment le nom DNS de la Gateway utilisée
dans le ficher `hosts` local.

    # localhost name resolution is handled within DNS itself.
    #	127.0.0.1       localhost
    #	::1             localhost
    192.168.99.109 gateway.horoquartz.poc

Dans le répertoire projet client-ui, construction de la distribution :

    rm -dr dist
    grunt clean
    grunt build --production
    
Dans le répertoire projet client-ui/docker, copie de la distribution, création de la machine Docker
puis envoi de cette machine sur le registry privé :

    cp -r ../dist .
    docker build -t hq/client-ui .
    docker tag hq/client-ui $(docker-machine ip registry):5000/hq/client-ui
    docker push $(docker-machine ip registry):5000/hq/client-ui
    docker rmi -f $(docker images -q hq/client-ui)
    docker rmi -f $(docker images -q httpd)
    
## Création des machines Docker pour les applications Spring Boot ##
Les directives de construction et de déploiement du conteneur Docker pour les applications Spring
Boot sont contenues dans le POM du projet parent, au sein de la définition du profil `cloud`.
<br>
Dans le répertoire du projet Spring Boot choisi, construction et déploiement du conteneur Docker
correspondant :

    eval $(docker-machine env swarm-master)
    export ENV_DOCKER_REGISTRY_HOST=$(docker-machine ip registry)
    mvn clean install -Pcloud
    
On utilise obligatoirement pour la construction une machine Docker pour laquelle le registry a été
enregistré (option `--engine-insecure-registry`). Attention à ne pas se placer dans le cluster
(option `--warm`), sinon la commande `docker push` échouera.

On peut éventuellement supprimer les tags et images créés localement sur la machine choisie pour
la construction (utiliser la commande `docker images` pour les détecter).

## Provisionnement et instanciation des conteneurs de l'application
Le fichier de description du cluster est le suivant :

    version: '2'

    services:
      web-ui:
        image: ${ENV_DOCKER_REGISTRY_HOST}:5000/hq/client-ui
        networks:
          - frontend
        ports:
          - "80:80"
        environment:
          - "constraint:capacity==ui"

      discovery:
        image: ${ENV_DOCKER_REGISTRY_HOST}:5000/hq/discovery
        hostname: discovery
        networks:
          - backend
        environment:
          - "constraint:capacity!=activity-service"
        ports:
          - "8761:8761"

      admin:
        image: ${ENV_DOCKER_REGISTRY_HOST}:5000/hq/admin
        hostname: admin
        networks:
          - backend
        environment:
          - "constraint:capacity!=activity-service"
        ports:
          - "8081:8081"

      activity:
        image: ${ENV_DOCKER_REGISTRY_HOST}:5000/hq/activity
        hostname: activity
        networks:
          - backend
        ports:
          - "8080:8080"
        environment:
          - "constraint:capacity==activity-service"

    networks:
      frontend:
        driver: overlay
      backend:
        driver: overlay

On remarque que deux réseaux sont créés afin d'isoler les compoosants qui n'ont aucune relation.
<br>
Le lancement de la composition s'effectue avec la commande :

    docker-compose up -d

L'arrête du cluster s'effectue avec la commande :

    docker-compose down

L'état du cluster s'analyse avec les commandes :

    docker-compose ps -a
    docker ps -a
