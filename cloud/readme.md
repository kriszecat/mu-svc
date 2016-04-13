# Création du cluster Docker #
----------
NB: Les machines Docker sont créées ici avec le driver virtualbox, notamment parce que ce cluster Docker a été testé sur un poste Windows.
Dans un environnement Linux on premise (VM ou bare metal), on utilisera plutôt le [driver générique](https://docs.docker.com/machine/drivers/generic/ "Docker generic driver").

 
Lors de la création des machines Docker, on pourra par ailleurs considérer une alternative récente au format de stockage aufs (cf. [Docker and OverlayFS in practice](https://docs.docker.com/engine/userguide/storagedriver/overlayfs-driver/ "Docker and OverlayFS in practice")). Dans ce cas, on ajoutera l'option `--engine-storage-driver overlay` lors de la création de la machine Docker.
 
## Création du service de registry ##
Création d'une machine Docker pour héberger le service de registry :
> docker-machine create -d virtualbox --virtualbox-disk-size "10000" --virtualbox-cpu-count "1" --virtualbox-memory "512" registry

Connexion à cette machine: 
> eval $(docker-machine env registry)

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
          - ENV_DOCKER_REGISTRY_HOST=192.168.99.110
          - ENV_DOCKER_REGISTRY_PORT=5000
        depends_on:
          - registry

Attention, le nom d'hôte ou l'@ IP associée à la variable `ENV_DOCKER_REGISTRY_HOST` est obtenue avec la commande $(docker-machine ip registry).   

=> lancement de la composition

> docker-compose up -d

## Création du service de découverte ##
Création d'une machine Docker pour héberger le service de découverte :

> docker-machine create -d virtualbox --virtualbox-disk-size "2000" --virtualbox-cpu-count "1" --virtualbox-memory "512" --engine-insecure-registry http://$(docker-machine ip registry):5000 discovery

Connexion à cette machine:
> eval $(docker-machine env discovery)

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
> docker-compose up -d

On utilise le serveur Consul pour la découverte.<br>
Pour la justification du choix de cette solution, cf. [Consul vs. ZooKeeper, doozerd, etcd](https://www.consul.io/intro/vs/zookeeper.html "Consul vs. ZooKeeper, doozerd, etcd")<br>

## Cluster Docker Swarm ##
### Création des machines associées aux noeuds du cluster
Création d'un master Swarm qui utilise le service de découverte :

> docker-machine create -d virtualbox 
 --virtualbox-disk-size "2000" --virtualbox-cpu-count "1" --virtualbox-memory "512"
 --engine-insecure-registry $(docker-machine ip registry):5000
 --swarm --swarm-master --swarm-discovery consul://$(docker-machine ip discovery):8500
 --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500
 --engine-opt cluster-advertise=eth1:2376
 swarm-master

Création de deux nouveaux noeuds dans le cluster qui utilisent le service de découverte:

> docker-machine create -d virtualbox
 --virtualbox-disk-size "2000" -virtualbox-cpu-count "1" --virtualbox-memory "512"
 --engine-insecure-registry $(docker-machine ip registry):5000
 --swarm --swarm-discovery consul://$(docker-machine ip discovery):8500
 --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500
 --engine-opt cluster-advertise=eth1:2376
 --engine-label capacity=rest-service
 swarm-node01

> docker-machine create -d virtualbox
 --virtualbox-disk-size "2000" -virtualbox-cpu-count "1" --virtualbox-memory "512"
 --engine-insecure-registry $(docker-machine ip registry):5000
 --swarm --swarm-discovery consul://$(docker-machine ip discovery):8500
 --engine-opt cluster-store=consul://$(docker-machine ip discovery):8500
 --engine-opt cluster-advertise=eth1:2376
 --engine-label capacity=rest-service
 swarm-node02

### Analyse du cluster créé
Positionnement de l'environnement client Docker sur le master (noter l'usage de l'option `--swarm`) :
> eval "$(docker-machine env --swarm swarm-master)"

Analyse du cluster

> docker-machine ls
<br>
> docker-machine inspect --format='{{json .Driver}}' swarm-master  
> docker info

Interrogation du service de découverte pour lister les noeuds appartenant au cluster :

> docker run swarm list consul://$(docker-machine ip discovery):8500

Analyse du réseau virtuel créé par Docker Swarm :
> docker network ls

Pour rejoindre le cluster au besoin après la création de la machine :
> docker run -d swarm join --addr $(docker-machine ip swarm-node01):2376 consul://$(docker-machine ip discovery):8500

### Création de la machine Docker client-ui
Dans le répertoire projet client-ui, construction de la distribution :
> rm -dr dist
> <br>
> grunt clean
> <br>
> grunt build --production

Dans le répertoire projet client-ui/docker, copie de la distribution, création de la machine Docker puis envoi de cette machine sur le registry privé :
> cp -r ../dist .
> <br>
> docker build -t hq/client-ui .
> <br>
> docker tag hq/client-ui $(docker-machine ip registry):5000/hq/client-ui
> <br>
> docker push $(docker-machine ip registry):5000/hq/client-ui
> <br>
> docker rmi -f $(docker images -q hq/client-ui)
> <br>
> docker rmi -f $(docker images -q httpd)

### Création de la machine Docker activity service
Dans le répertoire projet activity, construction de la distribution :
> mvn clean package

Création de la machine Docker puis envoi de cette machine sur le registry privé :
> docker build -t hq/activity .
> <br>
> docker tag hq/activity $(docker-machine ip registry):5000/hq/activity
> <br>
> docker push $(docker-machine ip registry):5000/hq/activity
> <br>
> docker rmi -f $(docker images -q hq/activity)
> <br>
> docker rmi -f $(docker images -q java)

