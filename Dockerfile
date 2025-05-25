# Multi-stage build for Spring Boot application
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first for better layer caching
COPY demo/pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy source code
COPY demo/src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-alpine

# Install curl for health checks
RUN apk add --no-cache curl

# Create app directory
WORKDIR /app

# Create non-root user for security
RUN addgroup -g 1001 -S appuser && \
    adduser -S appuser -G appuser

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8090

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8090}/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"] 