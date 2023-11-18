FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar gateway.jar
COPY config/* .

EXPOSE 9000

CMD ["java","-jar","/gateway.jar"]
