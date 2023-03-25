# syntax=docker/dockerfile:1
#
# Build stage
#
# Use JDK runtime as a parent image.
FROM eclipse-temurin:17-jdk-alpine AS BuildStage
ENV APP_HOME=/app/
# Set the working directory to /app.
WORKDIR $APP_HOME
# Copy the Gradle build and Source code files to the Build Stage Container.
COPY . $APP_HOME
# Build the project with Gradle.
RUN ./gradlew build

#
# Run stage
#
# Use a JDK runtime as a parent image.
FROM eclipse-temurin:17-jdk-alpine
ENV APP_HOME=/app/
# Create a Volume to persist the JAR file.
VOLUME $APP_HOME
# Copy the Build Stage JAR file to the Run Stage Container Volume.
COPY --from=BuildStage $APP_HOME/build/libs/fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar $APP_HOME
# Set the working directory to /app, so we don't need to prefix the CMD Layer with /app.
WORKDIR $APP_HOME
# Expose port 8080
EXPOSE 8080
# TODO Add Google Chrome Browser here, as a replacement for the Heroku Buildpack.
# Start the Spring Boot app
CMD ["java", "-jar", "fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar"]
