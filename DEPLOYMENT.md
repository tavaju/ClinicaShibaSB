# Clinica Shiba - Docker Deployment Guide

This guide explains how to deploy the Clinica Shiba Spring Boot application using Docker and Render.com.

## 📋 Prerequisites

- Docker and Docker Compose installed
- Git repository with your code
- Render.com account (free tier available)

## 🏗️ Local Development with Docker

### 1. Build the Docker Image

```bash
# Make the build script executable (if not already)
chmod +x scripts/build.sh

# Build the Docker image
./scripts/build.sh
```

### 2. Run with Docker Compose

```bash
# Start the application
docker-compose up

# Start in detached mode
docker-compose up -d

# View logs
docker-compose logs -f

# Stop the application
docker-compose down
```

### 3. Run Docker Container Directly

```bash
# Run the container
docker run -p 8090:8090 \
  -e SPRING_PROFILES_ACTIVE=production \
  -e CORS_ALLOWED_ORIGINS=http://localhost:4200 \
  clinica-shiba:latest
```

## 🚀 Deployment to Render.com

### Option 1: Using render.yaml (Recommended)

1. **Push your code to GitHub/GitLab**
   ```bash
   git add .
   git commit -m "Add Docker configuration for deployment"
   git push origin main
   ```

2. **Connect to Render.com**
   - Go to [Render.com](https://render.com)
   - Sign up/Login with your GitHub/GitLab account
   - Click "New +" → "Blueprint"
   - Connect your repository
   - Render will automatically detect the `render.yaml` file

3. **Configure Environment Variables** (if needed)
   - `SPRING_PROFILES_ACTIVE`: `production`
   - `CORS_ALLOWED_ORIGINS`: Your frontend domain

### Option 2: Manual Web Service Creation

1. **Create a New Web Service**
   - Go to Render Dashboard
   - Click "New +" → "Web Service"
   - Connect your repository

2. **Configure the Service**
   - **Name**: `clinica-shiba-backend`
   - **Environment**: `Docker`
   - **Region**: Choose closest to your users
   - **Branch**: `main`
   - **Dockerfile Path**: `./Dockerfile`

3. **Set Environment Variables**
   ```
   SPRING_PROFILES_ACTIVE=production
   PORT=8090
   CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
   ```

4. **Configure Health Check**
   - **Health Check Path**: `/actuator/health`

5. **Add Persistent Disk** (Optional)
   - **Name**: `clinica-shiba-data`
   - **Mount Path**: `/app/data`
   - **Size**: 1GB (free tier)

## 🔍 Verification

### Health Check Endpoints

- **Health**: `https://your-app.onrender.com/actuator/health`
- **API Documentation**: `https://your-app.onrender.com/swagger-ui.html`

### Local Testing

```bash
# Check health endpoint
curl http://localhost:8090/actuator/health

# Expected response:
# {"status":"UP"}
```

## 📊 Monitoring and Logs

### Render.com Dashboard
- View deployment logs in the Render dashboard
- Monitor resource usage
- Check health check status

### Docker Logs (Local)
```bash
# View container logs
docker-compose logs -f clinica-shiba

# View specific container logs
docker logs <container-id>
```

## 🛠️ Troubleshooting

### Common Issues

1. **Build Failures**
   ```bash
   # Clean Docker cache
   docker system prune -a
   
   # Rebuild without cache
   docker build --no-cache -t clinica-shiba:latest .
   ```

2. **Port Issues**
   - Ensure port 8090 is not in use locally
   - Check Render.com uses the PORT environment variable

3. **Database Issues**
   - H2 database files are persisted in `/app/data`
   - Check disk mount configuration on Render.com

4. **CORS Issues**
   - Update `CORS_ALLOWED_ORIGINS` environment variable
   - Include your frontend domain

### Debug Commands

```bash
# Enter running container
docker exec -it <container-name> /bin/bash

# Check container processes
docker exec -it <container-name> ps aux

# Check disk usage
docker exec -it <container-name> df -h
```

## 🔧 Configuration Files

- **Dockerfile**: Multi-stage build configuration
- **docker-compose.yml**: Local development setup
- **render.yaml**: Render.com deployment configuration
- **application-production.properties**: Production settings

## 📝 Notes

- The application uses H2 file-based database for persistence
- Health checks are configured for both Docker and Render.com
- CORS is configured for frontend integration
- Logs are optimized for production (reduced verbosity)
- Security is configured to allow health check endpoints

## 🆘 Support

If you encounter issues:
1. Check the deployment logs in Render.com dashboard
2. Verify environment variables are set correctly
3. Test the Docker image locally first
4. Check the health endpoint response 