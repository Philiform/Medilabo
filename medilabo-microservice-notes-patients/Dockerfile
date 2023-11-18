FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar microservice-notes-patients.jar
COPY config/* .

EXPOSE 9002

CMD ["java","-jar","/microservice-notes-patients.jar"]
