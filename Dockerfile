FROM maven:3.8.6-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Make sure we use the environment variables provided by Railway
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://${PGHOST}:${PGPORT}/${PGDATABASE}
ENV SPRING_DATASOURCE_USERNAME=${PGUSER}
ENV SPRING_DATASOURCE_PASSWORD=${PGPASSWORD}

# Add these environment variables to configure Spring Boot Actuator
ENV MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info
ENV MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS=always
ENV MANAGEMENT_HEALTH_PROBES_ENABLED=true
ENV MANAGEMENT_ENDPOINT_HEALTH_PROBES_ADD_ADDITIONAL_PATHS=true

EXPOSE 8080
# Add a startup delay to ensure database connection is established
CMD ["sh", "-c", "sleep 10 && java -jar app.jar"]