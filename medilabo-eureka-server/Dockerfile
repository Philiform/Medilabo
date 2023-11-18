FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar eureka-server.jar
COPY config/* .

EXPOSE 9102

CMD ["java","-jar","/eureka-server.jar"]
