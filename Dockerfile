# syntax=docker/dockerfile:1
#
# Build stage
#
# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine AS BuildStage
# Set the working directory to /app
ENV APP_HOME=/app/
WORKDIR $APP_HOME
# Copy the Gradle build and Source code files to the container
COPY . $APP_HOME
# Build the project with Gradle
RUN ./gradlew build

#
# Run stage
#
# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine
# Set the working directory to /app
ENV ARTIFACT_NAME=fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app/
VOLUME $APP_HOME
WORKDIR $APP_HOME
# Copy the built JAR file to the container
COPY --from=BuildStage $APP_HOME/build/libs/$ARTIFACT_NAME $APP_HOME
# Expose port 8080
EXPOSE 8080
# Start the Spring Boot app
CMD ["java", "-jar", "fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar"]
