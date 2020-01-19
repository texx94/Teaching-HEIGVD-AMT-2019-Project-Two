FROM openjdk:8-jdk-alpine

COPY auth-server-1.0.jar auth-server.jar
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "auth-server.jar"]