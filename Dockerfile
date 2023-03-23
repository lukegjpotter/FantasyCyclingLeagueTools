# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory to /app
WORKDIR /app

# Copy the Gradle build files to the container
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Build the project with Gradle
RUN ./gradlew build -x test

# Copy the built JAR file to the container
COPY build/libs/fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar /app/fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Start the Spring Boot app
CMD ["java", "-jar", "fantasy-cycling-league-tools-0.0.1-SNAPSHOT.jar]