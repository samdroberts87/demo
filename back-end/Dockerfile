# lightweight JDK base image which is (last time i checked vulnerability free
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy the built jar file into the image and rename as app.jar
COPY target/back-end-0.0.1-SNAPSHOT.jar app.jar

# expose the port as per the application.properties file
EXPOSE 7000

# Healthcheck to ensure endpoint is available else exit with code 1
HEALTHCHECK CMD curl --fail http://localhost:7000/actuator/health  || exit 1

# set profile to prod
ENV SPRING_PROFILES_ACTIVE=prod

# Run the jar file as you would in the CLI
ENTRYPOINT ["java", "-jar", "app.jar"]