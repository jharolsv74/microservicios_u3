# Dockerfile para microservicio de medicos
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/medicos.jar medicos.jar
ENTRYPOINT ["java","-jar","/medicos.jar"]
