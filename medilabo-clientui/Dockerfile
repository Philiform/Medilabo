FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar microservice-clientui.jar
COPY config/* .

EXPOSE 9102

CMD ["java","-jar","/microservice-clientui.jar"]
