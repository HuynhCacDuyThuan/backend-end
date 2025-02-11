# Use OpenJDK 17 base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/logistist-0.0.1-SNAPSHOT.jar logistist.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "logistist.jar"]
