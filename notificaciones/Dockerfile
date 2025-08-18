# Dockerfile para microservicio de notificaciones
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/notificaciones.jar notificaciones.jar
ENTRYPOINT ["java","-jar","/notificaciones.jar"]
