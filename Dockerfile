# syntax=docker/dockerfile:1
#
# Build stage
#
# Use Gradle runtime as a parent image. Gradle Deamon still takes time to start.
FROM gradle:8.8-jdk17-alpine AS buildstage
ENV APP_HOME=/app
# Set the working directory to /app.
WORKDIR $APP_HOME
# Copy the Gradle build and Source code files to the Build Stage Container.
COPY . $APP_HOME/
# The startup uses this Environmental Variable to decide between Chrome (on local) and Chromium (on Docker).
ENV IS_FANTASY_CYCLING_TOOLS_ON_DOCKER="true"
# Build the project with the Image's Gradle, so we don't have to download the Gradle.bin.zip.
RUN gradle clean build -x test

#
# Run stage
#
# Use a JDK runtime as a parent image.
FROM eclipse-temurin:17-jdk-alpine AS runstage
ENV APP_HOME=/app
# Add Chromium Browser for Selenium and WebDriverManager to use. FixMe: chromedriver is still downloaded by Boni Garcia
RUN apk add chromium chromium-chromedriver
# The startup uses this Environmental Variable to decide between Chrome (on local) and Chromium (on Docker).
ENV IS_FANTASY_CYCLING_TOOLS_ON_DOCKER="true"
# Create a Volume to persist the JAR file. ToDo: possibly remove this.
VOLUME $APP_HOME
# Copy the Build Stage JAR file to the Run Stage Container Volume.
COPY --from=buildstage $APP_HOME/build/libs/fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar $APP_HOME/fantasy-cycling-league-tools.jar
# Set the working directory to /app, so we don't need to prefix the CMD Layer with /app.
WORKDIR $APP_HOME
# Expose port 8080
EXPOSE 8080
# Start the Spring Boot app
CMD ["java", "-jar", "fantasy-cycling-league-tools.jar"]
