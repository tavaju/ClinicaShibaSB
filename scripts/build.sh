#!/bin/bash

# Build script for Clinica Shiba Docker image

echo "🏗️  Building Clinica Shiba Docker image..."

# Build the Docker image
docker build -t clinica-shiba:latest .

if [ $? -eq 0 ]; then
    echo "✅ Docker image built successfully!"
    echo "📦 Image: clinica-shiba:latest"
    
    # Show image size
    echo "📊 Image size:"
    docker images clinica-shiba:latest --format "table {{.Repository}}\t{{.Tag}}\t{{.Size}}"
    
    echo ""
    echo "🚀 To run the container locally:"
    echo "   docker-compose up"
    echo ""
    echo "🌐 To run just the container:"
    echo "   docker run -p 8090:8090 -e SPRING_PROFILES_ACTIVE=production clinica-shiba:latest"
    echo ""
    echo "🔍 Health check endpoint:"
    echo "   http://localhost:8090/actuator/health"
else
    echo "❌ Docker build failed!"
    exit 1
fi 