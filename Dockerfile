# Use a Java base image
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory
WORKDIR /app


# Copy the application's jar file to the container
COPY target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]