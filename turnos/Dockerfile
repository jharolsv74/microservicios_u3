# Dockerfile para microservicio de turnos
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/turnos.jar turnos.jar
ENTRYPOINT ["java","-jar","/turnos.jar"]
