FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copia solo pom.xml y descarga dependencias (cacheable)
COPY demo/pom.xml .

RUN mvn dependency:go-offline -B

# Ahora sí copia todo el código fuente incluyendo resources
COPY demo /app

# Empaqueta la aplicación
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache curl

WORKDIR /app

RUN addgroup -g 1001 -S appuser && \
    adduser -S appuser -G appuser

COPY --from=build /app/target/*.jar app.jar

RUN chown -R appuser:appuser /app

USER appuser

EXPOSE 8090

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8090}/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
