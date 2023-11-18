FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar config-server.jar
COPY config/* .

EXPOSE 9101

CMD ["java","-jar","/config-server.jar"]
