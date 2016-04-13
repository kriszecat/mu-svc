FROM java:8-jdk
EXPOSE 8080
COPY target/activity-1.0-SNAPSHOT.jar /opt/service/
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /opt/service/activity-1.0-SNAPSHOT.jar
