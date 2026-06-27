FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/rabbitmq-springboot-demo.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]