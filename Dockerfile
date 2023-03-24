#
# Build stage
#
# Use an official OpenJDK runtime as a parent image
FROM gradle:jdk11-jammy AS build
# Set the working directory to /app
WORKDIR /app
# Copy the Gradle build and Source code files to the container
COPY build.gradle settings.gradle gradlew settings.gradle system.properties /app/
COPY gradle /app/gradle
COPY src /app/src
# Build the project with Gradle
RUN gradle build

#
# Package stage
#
# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:11-jdk-jammy
# Copy the built JAR file to the container
COPY /app/build/libs/fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar /app/fantasy-cycling-league-tools.jar
# Expose port 8080
EXPOSE 8080
# Start the Spring Boot app
CMD ["java", "-jar", "/app/fantasy-cycling-league-tools.jar"]