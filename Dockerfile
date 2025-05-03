FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY demo ./demo
WORKDIR /app/demo
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/demo/target/*.jar app.jar
COPY application.properties ./application.properties

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:./application.properties"]
