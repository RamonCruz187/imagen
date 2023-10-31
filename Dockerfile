
FROM amazoncorretto:20-alpine-jdk
WORKDIR /app
VOLUME /tmp
MAINTAINER RamonCruz
COPY target/imagen-0.0.1-SNAPSHOT.jar crud.jar 
ENTRYPOINT ["java","-jar","/crud.jar"]
EXPOSE 8095
