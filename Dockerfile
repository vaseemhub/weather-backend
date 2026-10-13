# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Install wget for downloading maven-wrapper if needed
RUN apk add --no-cache wget

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download maven-wrapper.jar if not present
RUN if [ ! -f .mvn/wrapper/maven-wrapper.jar ]; then \
    wget -O .mvn/wrapper/maven-wrapper.jar https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar; \
    fi

# Download dependencies (cached layer)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/WeatherApp-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8081

# Environment variables
ENV WEATHER_API_KEY=${WEATHER_API_KEY}
ENV PORT=8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
