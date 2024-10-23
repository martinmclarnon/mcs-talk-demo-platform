# Step 1: Use a Gradle image to build the application
FROM gradle:8.0-jdk17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle configuration and source code
COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

RUN ./gradlew execute-bdd-tests --info

# Build the application
RUN gradle clean build --no-daemon

# Step 2: Use a minimal JDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage to the final image
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]