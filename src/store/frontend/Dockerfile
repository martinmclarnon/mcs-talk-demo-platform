# Use official Node.js LTS version as base image
FROM node:lts AS build-stage

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install
RUN npm install axios --save

# Copy the environment file - make sure this file exists or the build will fail
COPY .env.local ./

# Copy the rest of the application
COPY . .

# Build the Vue app
RUN npm run build

# Production stage
FROM nginx:stable-alpine as production-stage

# Copy build output from build-stage to NGINX HTML directory
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start NGINX server
CMD ["nginx", "-g", "daemon off;"]
