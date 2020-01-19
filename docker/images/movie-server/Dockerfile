FROM openjdk:8-jdk-alpine

COPY movie-server-1.0.jar movie-server.jar
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "movie-server.jar"]