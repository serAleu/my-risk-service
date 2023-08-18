# Build stage
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Package stage
FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY --from=build /home/app/target/my-risk-service-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["sh", "-c", "java -Dspring.profiles.active=$PROFILE -Dlogging.file.path=logs -jar app.jar"]