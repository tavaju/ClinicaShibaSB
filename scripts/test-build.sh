#!/bin/bash

# Test build script for ClinicaShiba
echo "🏗️  Testing Docker build for ClinicaShiba..."

# Build the Docker image
docker build -t clinica-shiba-test .

if [ $? -eq 0 ]; then
    echo "✅ Docker build successful!"
    echo "🧪 You can now test the application locally with:"
    echo "   docker run -p 8090:8090 clinica-shiba-test"
else
    echo "❌ Docker build failed!"
    exit 1
fi 