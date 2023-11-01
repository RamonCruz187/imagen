
FROM amazoncorretto:20-alpine-jdk
WORKDIR /app
VOLUME /tmp
MAINTAINER RamonCruz
COPY target/imagen-0.0.1-SNAPSHOT.jar app.jar 
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8095
