# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY demo ./demo
WORKDIR /app/demo
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/demo/target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
