# Dockerfile para microservicio de pacientes
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/pacientes.jar pacientes.jar
ENTRYPOINT ["java","-jar","/pacientes.jar"]
