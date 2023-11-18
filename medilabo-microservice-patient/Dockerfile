FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar microservice-patient.jar
COPY config/* .

EXPOSE 9001

CMD ["java","-jar","/microservice-patient.jar"]
