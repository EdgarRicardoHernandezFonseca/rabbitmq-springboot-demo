FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y wget

WORKDIR /app

COPY target/rabbitmq-springboot-demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]