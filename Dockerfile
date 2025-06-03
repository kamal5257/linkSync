# Use a base image with Java installed
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
ADD /target/LinkSync-0.0.2-SNAPSHOT.jar linksynk.jar

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "linksynk.jar"]

# Expose the port your application runs on (optional, depending on your app)
EXPOSE 8081
