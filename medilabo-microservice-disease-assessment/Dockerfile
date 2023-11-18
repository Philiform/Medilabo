FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar microservice-disease-assessment.jar
COPY config/* .

EXPOSE 9102

CMD ["java","-jar","/microservice-disease-assessment.jar"]
